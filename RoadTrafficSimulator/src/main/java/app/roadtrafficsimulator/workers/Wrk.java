package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.*;
import app.roadtrafficsimulator.controllers.IWrkCtrl;
import app.roadtrafficsimulator.exceptions.DBException;
import app.roadtrafficsimulator.helper.Physics;

import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author kucie
 */
public class Wrk implements ICtrlWrk {
    public Wrk(URL dbConfig) {
        ctrl = null;
        db = new DBWrk(dbConfig.getPath());
        account = null;

        circuit = circuitStraightRoad();
    }

    private Circuit circuitStraightRoad() {
        Circuit circuit = new Circuit();
        ArrayList<Roadable> rds = new ArrayList<>();
        Road rd1 = new Road(new Vec2(20,20), Direction.RIGHT, 30, 70 * Physics.KM_H);
        Road rd2 = new Road(new Vec2(50,20), Direction.RIGHT, 40, 50 * Physics.KM_H);
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

    Circuit circuit;
    private Account account;
    private DBWrk db;
    private IWrkCtrl ctrl;
}
