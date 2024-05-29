package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.workers.ICtrlWrk;

public interface ICtrl extends IWrkCtrl {
    void setWrk(ICtrlWrk wrk);
    void setApp(App app);
    void start();
    void terminate();
}
