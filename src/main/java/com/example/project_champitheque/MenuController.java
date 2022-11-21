package com.example.project_champitheque;

import com.example.project_champitheque.fileManager.Read;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class MenuController {
    MenuModel tmp;
    @FXML
    private Text pseudo;

    @FXML
    private Text score;


    @FXML
    private VBox containerRanking;


    @FXML
    private Button changePlayerButton;//boutton pour lancer jeu


    @FXML
    private Button MushMiner;//boutton pour lancer jeu

    @FXML
    private Button PowerMush;//boutton pour lancer jeu

    @FXML
    private Button GameTest;//boutton pour lancer jeu

    public void handleMushMiner() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MushMiner.fxml"));//deuième fichier fxml
        Stage window =(Stage) MushMiner.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(650);
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("MushMiner");
    }

    public void handlePowerMush() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PowerMush.fxml"));//deuième fichier fxml
        Stage window =(Stage) PowerMush.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("PowerMush");
    }

    public void handleGameTest() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("GameTest.fxml"));//deuième fichier fxml
        Stage window =(Stage) GameTest.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("PowerMush");
    }


    public void changePlayer() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ChoosePlayer.fxml"));//deuième fichier fxml
        Stage window =(Stage) changePlayerButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }

    public void initialize(){
        tmp = new MenuModel();
        score.textProperty().bind(tmp.scoreProperty().asString());
        pseudo.textProperty().bind(tmp.pseudoProperty());


        Read reader = new Read();
        List<List<String>> listRanking = reader.readAllFromFile("players");
        System.out.println(listRanking);
        listRanking.sort((elem1, elem2) -> Integer.parseInt(elem2.get(1)) - Integer.parseInt(elem1.get(1)));
        for(List<String> eachRanking : listRanking){
            Label nodeLine = new Label();
            nodeLine.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            nodeLine.setText(eachRanking.get(0)+" : "+eachRanking.get(1)+" points");
            containerRanking.getChildren().add(nodeLine);

        }
    }

}