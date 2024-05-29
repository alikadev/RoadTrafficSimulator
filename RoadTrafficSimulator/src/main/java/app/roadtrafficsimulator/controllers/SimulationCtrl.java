package app.roadtrafficsimulator.controllers;

import app.roadtrafficsimulator.App;
import app.roadtrafficsimulator.workers.ICtrlWrk;
import javafx.application.Platform;

public class SimulationCtrl implements ICtrl {
    @Override
    public void setWrk(ICtrlWrk wrk) {
        this.wrk = wrk;
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void start() {

    }

    @Override
    public void terminate() {
        Platform.exit();
    }

    private ICtrlWrk wrk;
    private App app;
}
