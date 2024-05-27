module app.roadtrafficsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    exports app.roadtrafficsimulator;
    
    opens app.roadtrafficsimulator.controllers;
    opens app.roadtrafficsimulator.views;
    opens app.roadtrafficsimulator.css;
    
    
}
