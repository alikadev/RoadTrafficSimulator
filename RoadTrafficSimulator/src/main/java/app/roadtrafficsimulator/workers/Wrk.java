package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.beans.*;
import app.roadtrafficsimulator.controllers.IWrkCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.exceptions.UnexpectedException;
import app.roadtrafficsimulator.helper.Physics;
import javafx.beans.NamedArg;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author kucie
 */
public class Wrk implements ICtrlWrk {
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
    }

    public ImageView getRoadTexture() {
        ImageView iv = new ImageView(roadTexture);
        iv.setSmooth(false);
        iv.setPreserveRatio(true);
        return iv;
    }

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
        Road rd1 = new Road(getRoadTexture(), new Vec2(-30,0), Direction.RIGHT, 30, 70 * Physics.KM_H);
        Road rd2 = new Road(getRoadTexture(), new Vec2(0,0), Direction.RIGHT, 40, 50 * Physics.KM_H);
        rd1.setNext(rd2);
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
        return db.verifyAccount(account);
    }

    @Override
    public Circuit getCircuit() {
        return circuit;
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
}
