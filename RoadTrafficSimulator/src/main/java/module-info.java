module app.roadtrafficsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.j;

    exports app.roadtrafficsimulator;
    
    opens app.roadtrafficsimulator.controllers;
    opens app.roadtrafficsimulator.views;
    opens app.roadtrafficsimulator.css;
    
    
}
