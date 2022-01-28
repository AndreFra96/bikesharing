module com.andreabrioschi.bikesharing {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.andreabrioschi.bikesharing to javafx.fxml;
    opens com.andreabrioschi.bikesharing.controllers to javafx.fxml;
    opens com.andreabrioschi.bikesharing.models to javafx.base;


    exports com.andreabrioschi.bikesharing;
}
