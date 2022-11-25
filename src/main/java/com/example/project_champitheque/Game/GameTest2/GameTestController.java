package com.example.project_champitheque.Game.GameTest2;

import com.example.project_champitheque.Application;
import com.example.project_champitheque.Game.GameController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.List;

public class GameTestController extends GameController {

    GameTestModel model;

    List<Item> list;

    @FXML
    private GridPane grid;

    public void NewGame() {
        model.resetGame();
    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        scoreNode1.setText(scoreTxt);
        popupend.setVisible(true);

    }

    public String getFileToReadStats(){
        return "GameTestScores";
    }
    public void setDifficulty(MouseEvent event){

    }


    public void initialize() {
        model = new GameTestModel();

        list = model.getList();

        callUpdate();
    }

    public void upDateGrid() {
        System.out.println("Affichage");
        grid.getChildren().clear();
        for(int i = 0; i < model.getSizeX(); i++){
            for(int j = 0; j < model.getSizeY(); j++){
                ImageView paneImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/0.png")));
                grid.add(paneImage, i, j);
            }
        }

        for (Item item : list) {
            Text txtNode = new Text(item.name+String.valueOf(item.life)+" hp");
            txtNode.setStyle("-fx-font: 18 arial;");
            txtNode.setFill(Color.WHITE);
            txtNode.setWrappingWidth(80);
            txtNode.setTextAlignment(TextAlignment.CENTER);
            grid.add(txtNode, item.getX(), item.getY());
        }
    }

    public void callUpdate(){
        upDateGrid();
    }




}
