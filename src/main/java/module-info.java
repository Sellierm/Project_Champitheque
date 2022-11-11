module com.example.project_champitheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.project_champitheque to javafx.fxml;
    exports com.example.project_champitheque;
}