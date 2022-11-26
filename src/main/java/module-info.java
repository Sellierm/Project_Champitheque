module com.example.project_champitheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.project_champitheque to javafx.fxml;
    exports com.example.project_champitheque;
    exports com.example.project_champitheque.Game.MushMiner;
    opens com.example.project_champitheque.Game.MushMiner to javafx.fxml;
    exports com.example.project_champitheque.Game.PowerMush;
    opens com.example.project_champitheque.Game.PowerMush to javafx.fxml;
    exports com.example.project_champitheque.Game.MushIdle;
    opens com.example.project_champitheque.Game.MushIdle to javafx.fxml;
    exports com.example.project_champitheque.Interfaces;
    opens com.example.project_champitheque.Interfaces to javafx.fxml;
    exports com.example.project_champitheque.Poubelle;
    opens com.example.project_champitheque.Poubelle to javafx.fxml;
    exports com.example.project_champitheque.Game.ChampiMerge;
    opens com.example.project_champitheque.Game.ChampiMerge to javafx.fxml;
    exports com.example.project_champitheque.Game;
    opens com.example.project_champitheque.Game to javafx.fxml;
}