package app.roadtrafficsimulator.workers;

import app.roadtrafficsimulator.beans.Account;
import app.roadtrafficsimulator.controllers.IWrkCtrl;
import app.roadtrafficsimulator.exceptions.DBException;

import java.net.URL;

/**
 *
 * @author kucie
 */
public class Wrk implements ICtrlWrk {
    public Wrk(URL dbConfig) {
        ctrl = null;
        db = new DBWrk(dbConfig.getPath());
        account = null;
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

    public void setCtrl(IWrkCtrl ctrl) {
        this.ctrl = ctrl;
    }

    private Account account;
    private DBWrk db;
    private IWrkCtrl ctrl;
}
