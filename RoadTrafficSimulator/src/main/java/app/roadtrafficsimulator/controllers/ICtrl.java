package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.workers.ICtrlWrk;

public interface ICtrl extends IWrkCtrl {
    void setWrk(ICtrlWrk wrk);
    void start();
    void terminate();
}
