package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Car;
import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.Roadable;
import app.roadtrafficsimulator.beans.Vehicle;

import java.util.ArrayList;

public class SimulationWrk {
    public SimulationWrk(ISimulationWrk wrk) {
        circuit = null;
        this.wrk = wrk;
        running = false;
        thr = null;
    }

    public void start(Circuit circuit, double speedFactor) {
        if (circuit == null)
            throw new RuntimeException("Cannot simulate a null circuit");

        this.speedFactor = speedFactor;
        this.circuit = circuit;
        running = true;
        thr = new Thread(this::run);
        thr.start();
    }

    public void terminate() {
        try {
            running = false;
            if (thr != null) {
                thr.join();
                thr = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to stop the simulation: " + e.getMessage());
        }
    }

    private void run() {
        long lastTime = System.nanoTime();
        while (running) {
            // Calculate deltatime (time change since the last time)
            long time = System.nanoTime();
            double deltaTime = ((time - lastTime) / 1000000000.0) * speedFactor;
            lastTime = time;

            // Update all vehicle
            ArrayList<Vehicle> toDestroy = new ArrayList<>();
            for (Vehicle v : circuit.getVehicles()) {
                if (!update(v, deltaTime))
                    toDestroy.add(v);
            }

            // Remove vehicles to destroy
            for (Vehicle v : toDestroy) {
                wrk.removeVehicle(v);
            }

            // Generate new cars on road
            for (Roadable rd : circuit.getRoads())
            {
                if (rd.shouldSpawnVehicle(deltaTime)) {
                    wrk.addVehicle(getCar(rd));
                }
            }

            // Sleeping to ease the JavaFX Thread
            // If we go too fast, the animation will become jiggly and some
            // bindings will die.
            // See the problem N°3 to better understand!
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    private boolean update(Vehicle v, double dt) {
        Roadable road = v.getRoad();
        double speed = v.getSpeed();

        // Calculate ac/deceleration velocity: (d/t^2) * t => d/t
        double accelerationVelocity = v.getAcceleration() * dt;
        double decelerationVelocity = v.getDeceleration() * dt;

        // Modify speed on acceleration: d/t + d/t
        speed += speed > road.getSpeedLimit()
                ? decelerationVelocity
                : accelerationVelocity;

        v.setSpeed(speed);

        // Calculate the distance: d/t * t => d
        double distance = speed * dt;

        // Move the car (depending on the road)
        return road.moveVehicle(v, distance);
    }

    private Car getCar(Roadable rd) {
        return new Car(wrk.getCarTexture(), (Car)circuit.getDefaultVehicle(), rd);
    }

    private ISimulationWrk wrk;
    private Thread thr;
    private volatile boolean running;
    private volatile Circuit circuit;
    private volatile double speedFactor;
}
