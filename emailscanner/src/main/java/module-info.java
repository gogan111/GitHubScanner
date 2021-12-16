module com.it.dreamteam.emailscanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires github.api;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    exports com.it.dreamteam.emailscanner;
    opens com.it.dreamteam.emailscanner.controller to javafx.fxml;
    opens com.it.dreamteam.emailscanner.model to javafx.base;
}
