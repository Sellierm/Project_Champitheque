package com.example.project_champitheque;

import com.example.project_champitheque.FileManager.Read;
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
    MenuModel model;
    @FXML
    private Text pseudo;

    @FXML
    private Text score;

    @FXML
    private Text lvl;


    @FXML
    private VBox containerRanking;


    @FXML
    private Button changePlayerButton;


    @FXML
    private Button MushMiner;
    @FXML
    private Text scoreMushMiner;
    @FXML
    private Text lvlMushMiner;
    @FXML
    private Text nbPlaysMushMiner;


    @FXML
    private Button PowerMush;
    @FXML
    private Text scorePowerMush;
    @FXML
    private Text lvlPowerMush;
    @FXML
    private Text nbPlaysPowerMush;

    @FXML
    private Button ChampiMerge;
    @FXML
    private Text scoreChampiMerge;
    @FXML
    private Text lvlChampiMerge;
    @FXML
    private Text nbPlaysChampiMerge;

    @FXML
    private Button MushIdle;
    @FXML
    private Text scoreMushIdle;
    @FXML
    private Text lvlMushIdle;
    @FXML
    private Text nbPlaysMushIdle;

    public void handleMushMiner() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MushMiner.fxml"));
        Stage window =(Stage) MushMiner.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(600);
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
        window.setTitle("ChampiMerge");
    }

    public void handleMushIdle() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("MushIdle.fxml"));
        Stage window =(Stage) MushIdle.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("MushIdle");
    }


    public void changePlayer() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("ChoosePlayer.fxml"));
        Stage window =(Stage) changePlayerButton.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }

    public void initialize(){
        model = new MenuModel();
        score.textProperty().bind(model.scoreProperty().asString());
        lvl.textProperty().bind(model.lvlProperty().asString());
        pseudo.textProperty().bind(model.pseudoProperty());




        Read reader = new Read();
        List<List<String>> listRanking = model.getAllData();
        System.out.println(listRanking);
        listRanking.sort((elem1, elem2) -> Integer.parseInt(elem2.get(1)) - Integer.parseInt(elem1.get(1)));
        for(List<String> eachRanking : listRanking){
            Label nodeLine = new Label();
            nodeLine.setStyle("-fx-font-size: 20px; -fx-text-fill: "+eachRanking.get(3)+";");
            nodeLine.setText(eachRanking.get(0)+" : "+eachRanking.get(1)+" points");
            containerRanking.getChildren().add(nodeLine);

        }


        List<String> MushMinerData = model.getMushMinerData();
        scoreMushMiner.setText(MushMinerData.get(0));
        lvlMushMiner.setText(MushMinerData.get(1));
        nbPlaysMushMiner.setText(MushMinerData.get(2));


        List<String> PowerMushData = model.getPowerMushData();
        scorePowerMush.setText(PowerMushData.get(0));
        lvlPowerMush.setText(PowerMushData.get(1));
        nbPlaysPowerMush.setText(PowerMushData.get(2));


        List<String> ChampiMergeData = model.getChampiMergeData();
        scoreChampiMerge.setText(ChampiMergeData.get(0));
        lvlChampiMerge.setText(ChampiMergeData.get(1));
        nbPlaysChampiMerge.setText(ChampiMergeData.get(2));


        List<String> MushIdleData = model.getMushIdleData();
        scoreMushIdle.setText(MushIdleData.get(0));
        lvlMushIdle.setText(MushIdleData.get(1));
        nbPlaysMushIdle.setText(MushIdleData.get(2));
    }

}