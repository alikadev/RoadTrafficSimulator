package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.workers.ICtrlWrk;

/**
 * This is the global controller interface. It extends the Worker's Controller interface and add some required methode
 * for the Application's Controllers manager.
 *
 * @author Elvin Kuci
 */
public interface ICtrl extends IWrkCtrl {
    /**
     * Set the worker's reference.
     *
     * @param wrk The worker's reference.
     */
    void setWrk(ICtrlWrk wrk);

    /**
     * Set the application's reference.
     * @param app The application's reference.
     */
    void setApp(App app);

    /**
     * This methode is called when the application is ready to be started. The worker will already be linked to the
     * controller at this point.
     */
    void start();

    /**
     * This methode is called when the application needs to be stopped.
     */
    void terminate();
}
