module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    exports app;
    
    opens app.controllers;
    opens app.views;
}
