package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.*;
import app.roadtrafficsimulator.controllers.IWrkCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.Physics;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kucie
 */
public class Wrk implements ICtrlWrk, ISimulationWrk {
    public Wrk() {
        carTextures = new ArrayList<>();

        // Load ressources
        try {
            // Verify ressources URLs
            URL dbConfigURL = getClass().getClassLoader().getResource(App.DB_CONFIG_LOCALHOST);
            if (dbConfigURL == null)
                throw new UnexpectedException("Ressource not found: " + App.DB_CONFIG_LOCALHOST);

            URL roadTextureURL = getClass().getClassLoader().getResource(App.ROAD_TEXTURE);
            if (roadTextureURL == null)
                throw new UnexpectedException("Ressource not found: " + App.ROAD_TEXTURE);

            // Verify and load all car textures
            for (int i = App.CAR_TEXTURE_START; i < App.CAR_TEXTURE_END; ++i) {
                URL carTextureURL = getClass().getClassLoader().getResource(App.CAR_TEXTURE_BASE + i + App.CAR_TEXTURE_EXTENSION);
                if (carTextureURL == null)
                    throw new UnexpectedException("Ressource not found: " + App.CAR_TEXTURE_BASE + " @" + i);
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

    private Circuit circuitStraightRoad() {
        Circuit circuit = new Circuit();
        ArrayList<Roadable> rds = new ArrayList<>();
        Road rd1 = new Road("RTS-C1-R01",getRoadTexture(), new Vec2(-40,0), Direction.RIGHT, 40, 70);
        Road rd2 = new Road("RTS-C1-R02",getRoadTexture(), new Vec2(0,0), Direction.RIGHT, 40, 30);
        rd1.setNext(rd2);
        rd1.setTraffic(30);
        rds.add(rd1);
        rds.add(rd2);

        circuit.setRoads(rds);

        return circuit;
    }

    public void start() throws DBException {
        db.start();
    }

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

    public void setCtrl(IWrkCtrl ctrl) {
        this.ctrl = ctrl;
    }

    private Circuit circuit;
    private Image roadTexture;
    private ArrayList<Image> carTextures;
    private Account account;
    private DBWrk db;
    private IWrkCtrl ctrl;
    private SimulationWrk simulator;
}
