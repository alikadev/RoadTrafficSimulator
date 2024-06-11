package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.*;
import app.roadtrafficsimulator.controllers.IWrkCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.UnexpectedException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main worker. Its role is mainly to redirect the calls to the good worker.
 *
 * @author kucie
 */
public class Wrk implements ICtrlWrk, ISimulationWrk {
    /**
     * Create the worker.
     */
    public Wrk() {
        carTextures = new ArrayList<>();

        // Load resources
        try {
            // Verify resources URLs
            URL dbConfigURL = getClass().getClassLoader().getResource(App.DB_CONFIG_EXTERNAL);
            if (dbConfigURL == null)
                throw new UnexpectedException("Resource not found: " + App.DB_CONFIG_EXTERNAL);

            URL roadTextureURL = getClass().getClassLoader().getResource(App.ROAD_TEXTURE);
            if (roadTextureURL == null)
                throw new UnexpectedException("Resource not found: " + App.ROAD_TEXTURE);

            // Verify and load all car textures
            for (int i = App.CAR_TEXTURE_START; i < App.CAR_TEXTURE_END; ++i) {
                URL carTextureURL = getClass().getClassLoader().getResource(App.CAR_TEXTURE_BASE + i + App.CAR_TEXTURE_EXTENSION);
                if (carTextureURL == null)
                    throw new UnexpectedException("Resource not found: " + App.CAR_TEXTURE_BASE + " @" + i);
                carTextures.add(new Image(carTextureURL.openStream()));
            }

            // Load resources
            db = new DBWrk(dbConfigURL.getPath());
            roadTexture = new Image(roadTextureURL.openStream());
        } catch (IOException e) {
            throw new UnexpectedException("Fail to load ressource: " + e.getMessage());
        }

        // Continue
        ctrl = null;
        account = null;
        circuit = circuitStraightRoad();
        simulator = new SimulationWrk(this);
    }

    /**
     * Starts the worker.
     *
     * @throws DBException In case of an exception, this is thrown.
     */
    public void start() throws DBException {
        db.start();
    }

    /**
     * Get the road's texture.
     * An ImageView is given back because the rotation is (nearly) only possible on this class.
     *
     * @return The ImageView containing the texture.
     */
    public ImageView getRoadTexture() {
        ImageView iv = new ImageView(roadTexture);
        iv.setSmooth(false);
        iv.setPreserveRatio(true);
        return iv;
    }

    @Override
    public ImageView getCarTexture() {
        int randomId = (int)(Math.random() * carTextures.size());
        ImageView iv = new ImageView(carTextures.get(randomId));
        iv.setSmooth(false);
        iv.setPreserveRatio(true);
        return iv;
    }

    /**
     * Generate the "straight road circuit"!
     *
     * @return The circuit's instance.
     */
    private Circuit circuitStraightRoad() {
        Circuit circuit = new Circuit();
        ArrayList<Roadable> rds = new ArrayList<>();

        // 2 roads...
        Road rd1 = new Road("RTF-C1-R01",getRoadTexture(), new Vec2(-40,0), Direction.RIGHT, 40, 70);
        Road rd2 = new Road("RTF-C1-R02",getRoadTexture(), new Vec2(0,0), Direction.RIGHT, 40, 30);
        // Linked together...
        rd1.setNext(rd2);
        // With some traffic...
        rd1.setTraffic(30);
        rds.add(rd1);
        rds.add(rd2);

        circuit.setRoads(rds);

        return circuit;
    }

    /**
     * Terminate any action on the worker
     *
     * @throws RuntimeException Is case of an error, this will be thrown.
     */
    public void terminate() throws RuntimeException {
        try {
            db.terminate();
            simulator.terminate();
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createAccount(Account account) throws DBException {
        account.setPassword(HashWrk.hash(account.getPassword()));
        db.insertAccount(account);
        this.account = account;
    }

    @Override
    public boolean verifyAccount(Account account) throws DBException {
        account.setPassword(HashWrk.hash(account.getPassword()));
        if (db.verifyAccount(account)) {
            this.account = account;
            return true;
        }

        return false;
    }

    @Override
    public List<String> getSettingsSetsList() throws DBException {
        return db.getSettingsSetsList(account);
    }

    @Override
    public void createSettingsSet(SettingsSet set) throws DBException {
        if (db.containsSettingsSet(account, set.getName()))
            throw new DBException("Le jeu de réglages " + set.getName() + " éxiste déjà dans la base de donnée.");

        db.insertSettingsSet(account, set);
    }

    @Override
    public void updateSettingsSet(SettingsSet set) throws DBException {
        db.insertSettingsSet(account, set);
    }

    @Override
    public SettingsSet loadSettingsSet(String setName) throws DBException {
        return db.getSettingsSet(account, setName);
    }

    @Override
    public void applySettingsSet(SettingsSet set) {
        for (Roadable rd : circuit.getRoads()) {
            rd.setSettings(set.getSettings());
        }
    }

    @Override
    public Circuit getCircuit() {
        return circuit;
    }

    @Override
    public void startSimulation() {
        simulator.start(circuit, ctrl.getSpeedFactor());
    }

    @Override
    public void stopSimulation() {
        simulator.terminate();
    }

    @Override
    public void addVehicle(Vehicle v) {
        // Add the vehicle to the list
        List<Vehicle> a = circuit.getVehicles();
        a.add(v);
        circuit.setVehicles(a);

        // Render the vehicle
        ctrl.addVehicle(v);
    }

    @Override
    public void removeVehicle(Vehicle v) {
        ctrl.removeVehicle(v);
        circuit.getVehicles().remove(v);
    }

    /**
     * Set the controller's reference.
     *
     * @param ctrl The controller's reference.
     */
    public void setCtrl(IWrkCtrl ctrl) {
        this.ctrl = ctrl;
    }

    /**
     * The current circuit.
     */
    private Circuit circuit;

    /**
     * The road's texture
     */
    private final Image roadTexture;

    /**
     * The list of car's texture.
     */
    private final ArrayList<Image> carTextures;

    /**
     * The currently logged in account.
     */
    private Account account;

    /**
     * The database worker's instance.
     */
    private final DBWrk db;

    /**
     * The simulation worker's instance.
     */
    private final SimulationWrk simulator;

    /**
     * The controller's reference.
     */
    private IWrkCtrl ctrl;
}
