package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.workers.ICtrlWrk;

/**
 *
 * @author kucie
 */
public interface IAppCtrl {
    /**
     * This function is called when the controller is ready. The ICtrlWrk
     * reference should be set.
     */
    public void start();
    
    /**
     * This function is called when the application need to be stopped. You need
     * to stop any process or thread that you started on the ICtrlWrk!
     */
    public void terminate();
    
    /**
     * Set the worker reference.
     * @param wrk The worker. It shouldn't be changed while started!
     */
    public void setWrk(ICtrlWrk wrk);
}
