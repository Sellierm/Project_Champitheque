module com.example.project_champitheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.project_champitheque to javafx.fxml;
    exports com.example.project_champitheque;
    exports com.example.project_champitheque.MushMiner;
    opens com.example.project_champitheque.MushMiner to javafx.fxml;
    exports com.example.project_champitheque.PowerMush;
    opens com.example.project_champitheque.PowerMush to javafx.fxml;
    exports com.example.project_champitheque.GameTest2;
    opens com.example.project_champitheque.GameTest2 to javafx.fxml;
    exports com.example.project_champitheque.Interfaces;
    opens com.example.project_champitheque.Interfaces to javafx.fxml;
    exports com.example.project_champitheque.Poubelle;
    opens com.example.project_champitheque.Poubelle to javafx.fxml;
    exports com.example.project_champitheque.ChampiMerge;
    opens com.example.project_champitheque.ChampiMerge to javafx.fxml;
}