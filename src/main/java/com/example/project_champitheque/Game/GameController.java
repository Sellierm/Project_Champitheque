package com.example.project_champitheque.Game;

import com.example.project_champitheque.Application;
import com.example.project_champitheque.Interfaces.Help;
import com.example.project_champitheque.Interfaces.NewGame;
import com.example.project_champitheque.Interfaces.PopUpEnd;
import com.example.project_champitheque.Interfaces.Quit;
import com.example.project_champitheque.Game.PowerMush.Joueur;
import com.example.project_champitheque.FileManager.Read;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class GameController implements Quit, Help, NewGame, PopUpEnd {
    @FXML
    private Button quit;

    @FXML
    private Button help;

    @FXML
    private Button newGame;

    @FXML
    protected Pane popupend;
    @FXML
    protected Text scoreNode1;
    @FXML
    protected Text scoreNode2;


    @FXML
    private Pane popuphelp;


    @FXML
    private Pane ranking;
    @FXML
    private VBox containerRanking;


    @FXML
    private ImageView diff1;

    @FXML
    private ImageView diff2;

    @FXML
    private ImageView diff3;

    public List<ImageView> tabDiff = new ArrayList<ImageView>();


    @Override
    public void Quit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) quit.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(600);
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("Champithèque");
    }

    public void Help(){
        popuphelp.setVisible(true);
    }

    public void CloseHelp(){
        popuphelp.setVisible(false);
    }

    public abstract void NewGame();

    public void ShowPopUpEnd(int score){
        popupend.setVisible(true);
    }
    public void ShowPopUpEnd(Joueur winner, int score){
        popupend.setVisible(true);
    }

    public void ClosePopUpEnd(){
        popupend.setVisible(false);

    }

    public void ShowStats(){
        ranking.setVisible(true);
        Read reader = new Read();
        List<List<String>> listRanking = reader.readAllFromFile(getFileToReadStats());
        listRanking.sort((elem1, elem2) -> Integer.parseInt(elem2.get(1)) - Integer.parseInt(elem1.get(1)));
        for(int i = 0; i < listRanking.size() && i < 10; i++){
            List<String> eachRanking = listRanking.get(i);
            String name = reader.getName(Integer.parseInt(eachRanking.get(0)));
            Label nodeLine = new Label();
            nodeLine.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            nodeLine.setText(name+" : "+eachRanking.get(1)+" points");
            containerRanking.getChildren().add(nodeLine);

        }
    }

    public abstract String getFileToReadStats();

    public void CloseStats(){
        ranking.setVisible(false);
        containerRanking.getChildren().clear();
    }


    public void initializeDifficulty(){
        //Set difficulty
        diff1.setScaleX(1.3);
        diff1.setScaleY(1.3);
        diff1.setUserData("1");
        diff2.setUserData("2");
        diff3.setUserData("3");
        this.tabDiff.add(diff1);
        this.tabDiff.add(diff2);
        this.tabDiff.add(diff3);
    }
    public abstract void setDifficulty(MouseEvent event);

}
