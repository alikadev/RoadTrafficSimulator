package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Circuit;
import app.roadtrafficsimulator.beans.Roadable;
import app.roadtrafficsimulator.beans.Vehicle;

import java.util.ArrayList;

public class SimulationWrk {
    public SimulationWrk(ISimulationWrk wrk) {
        circuit = null;
        this.wrk = wrk;
        running = false;
        thr = new Thread(this::run);
    }

    public void start(Circuit circuit) {
        if (circuit == null)
            throw new RuntimeException("Cannot simulate a null circuit");

        this.circuit = circuit;
        running = true;
        thr.start();
    }

    public void stop() {
        try {
            running = false;
            thr.join();
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to stop the simulation: " + e.getMessage());
        }
    }

    private void run() {
        long lastTime = System.nanoTime();
        while (running) {
            // Calculate deltatime (time change since the last time)
            long time = System.nanoTime();
            System.out.println(time);
            double deltaTime = ((time - lastTime) / 1000000000.0);
            lastTime = time;

            // Update all
            ArrayList<Vehicle> toDestroy = new ArrayList<>();
            for (Vehicle v : circuit.getVehicles()) {
                if (!update(v, deltaTime))
                    toDestroy.add(v);
            }

            for (Vehicle v : toDestroy) {
                System.out.println("Destroying " + v + "'s ass");
                stop();
                //wrk.removeVehicle(v);
            }
        }
    }

    private boolean update(Vehicle v, double dt) {
        Roadable road = v.getRoad();
        double speed = v.getSpeed();

        // Calculate ac/deceleration velocity: (d/t^2) * t => d/t
        double accelerationVelocity = v.getAcceleration() * dt;
        double decelerationVelocity = v.getDeceleration() * dt;

        System.out.println(v.getPosition());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {}

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

    private ISimulationWrk wrk;
    private Thread thr;
    private volatile boolean running;
    private volatile Circuit circuit;
}
