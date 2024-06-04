module app.roadtrafficsimulator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires mysql.connector.j;
    requires com.google.protobuf;

    exports app.roadtrafficsimulator;
    
    opens app.roadtrafficsimulator.controllers;
    opens app.roadtrafficsimulator.views;
    opens app.roadtrafficsimulator.css;
    opens app.roadtrafficsimulator.textures;
    opens app.roadtrafficsimulator.config.db;
}
