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
    private Button changePlayerButton;


    @FXML
    private Button MushMiner;

    @FXML
    private Button PowerMush;

    @FXML
    private Button ChampiMerge;

    @FXML
    private Button GameTest;

    public void handleMushMiner() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MushMiner.fxml"));
        Stage window =(Stage) MushMiner.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(650);
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("MushMiner");
    }

    public void handlePowerMush() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("PowerMush.fxml"));
        Stage window =(Stage) PowerMush.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("PowerMush");
    }

    public void handleChampiMerge() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ChampiMerge.fxml"));
        Stage window =(Stage) ChampiMerge.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("Game 2048");
    }

    public void handleGameTest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("GameTest.fxml"));
        Stage window =(Stage) GameTest.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("Game Test");
    }


    public void changePlayer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ChoosePlayer.fxml"));
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