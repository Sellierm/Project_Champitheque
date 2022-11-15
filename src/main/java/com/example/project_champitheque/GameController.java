package com.example.project_champitheque;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameController implements Quit, Help, NewGame, PopUpEnd {

    GameModel model;

    @FXML
    private Button quit;//boutton pour quitter le jeu

    @FXML
    private Button help;//boutton pour quitter le jeu

    @FXML
    private Button newGame;//boutton pour quitter le jeu

    public GridPane grid;

    @FXML
    private Text champiFind;//boutton pour quitter le jeu

    @FXML
    private Text restant;//boutton pour quitter le jeu

    @FXML
    private TextField inputSizeX;//boutton pour quitter le jeu

    @FXML
    private TextField inputSizeY;//boutton pour quitter le jeu


    @FXML
    private Pane popupend;

    @FXML
    private Text finalScore;

    @FXML
    private Text lvlEarned;




    @Override
    public void Quit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) quit.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }


    public void Help(){
    }


    public void NewGame() {
        if(Integer.parseInt(inputSizeX.getText()) >19)inputSizeX.setText("19");
        if(Integer.parseInt(inputSizeX.getText()) <10)inputSizeX.setText("10");
        if(Integer.parseInt(inputSizeY.getText()) >13)inputSizeY.setText("13");
        if(Integer.parseInt(inputSizeY.getText()) <10)inputSizeY.setText("10");
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model.restartGame(sizeX, sizeY,0.3);
        setGrilleFX();
        ClosePopUpEnd();
    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        String findTxt = ""+model.getChampiFind();
        finalScore.setText(findTxt);
        lvlEarned.setText(scoreTxt);
        popupend.setVisible(true);

    }

    public void ClosePopUpEnd(){
        popupend.setVisible(false);

    }





    public void initialize(){
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model = new GameModel(sizeX, sizeY,0.3);

        champiFind.textProperty().bind(model.champiFindProperty().asString());
        restant.textProperty().bind(model.restantProperty().asString());

        setGrilleFX();


        //On empêche les input de caractères autres que des nombres
        inputSizeX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputSizeX.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        inputSizeY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputSizeY.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }


    public void setGrilleFX(){
        grid.getChildren().clear();
        ArrayList<ArrayList> grille = model.getGrille();
        System.out.println(grid);
        grid.setPrefWidth(1000);
        grid.setPrefHeight(560);
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                String txt = x+"-"+y;
                Text txtNode = new Text(txt);
                txtNode.setUserData(txt);
                grid.add(txtNode, x, y);
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/grass.png")));
                selectedImage.setUserData(txt);
                grid.add(selectedImage, x, y);
            }
        }

        for(Node node : grid.getChildren()){
            node.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    clickedCase(e);

                }
                if(e.getButton() == MouseButton.SECONDARY){
                    placeBarriere(e);
                }
            });
        }

        System.out.println(grid);
        System.out.println(grid.getChildren());
    }


    //Fonction appelée au click sur une case pour afficher le résultat de la case
    public void clickedCase(MouseEvent e){
        if(model.getRestant() > 0) {
            ImageView target = (ImageView) e.getTarget();
            System.out.println(target.getUserData());

            String data = (String) target.getUserData();
            String[] arr = null;
            arr = data.split("-");
            List<String> list = Arrays.asList(arr);
            int resultCase = model.revealCase(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));


            ImageView resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/leaf.png")));
            Text txtNode = new Text("");

            if (resultCase == 1) {
                resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.jpg")));
            } else {
                int nbBombsAround = model.getNbBombsAround(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
                txtNode.setText(String.valueOf(nbBombsAround));
                txtNode.setStyle("-fx-font: 30 arial;");
                txtNode.setFill(Color.WHITE);
                txtNode.setTextAlignment(TextAlignment.CENTER);
            }


            grid.add(resultImage, Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
            grid.add(txtNode, Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));

            if(model.getRestant() <= 0) {
                revealGrid();
                ShowPopUpEnd(model.finalScore());
            }
        }
        else{
            revealGrid();
            ShowPopUpEnd(model.finalScore());
        }
    }


    public void placeBarriere(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        System.out.println(target.getUserData());

        String data = (String) target.getUserData();
        String[] arr = null;
        arr = data.split("-");
        List<String> list = Arrays.asList(arr);

        ImageView resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/barriere.png")));
        resultImage.setUserData(data);
        resultImage.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                clickedCase(event);

            }
            if(event.getButton() == MouseButton.SECONDARY){
                placeBarriere(event);
            }
        });
        grid.add(resultImage, Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
    }





    public void revealGrid(){
        grid.getChildren().clear();

        ArrayList<ArrayList> grille = model.getGrille();


        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;

                int resultCase = model.getCaseValue(x, y);


                ImageView resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/leaf.png")));
                Text txtNode = new Text();

                if(resultCase == 1){
                    resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.jpg")));
                }
                else {
                    int nbBombsAround = model.getNbBombsAround(x, y);
                    txtNode.setText(String.valueOf(nbBombsAround));
                    txtNode.setStyle("-fx-font: 30 arial;");
                    txtNode.setFill(Color.WHITE);
                    txtNode.setTextAlignment(TextAlignment.CENTER);
                }

                grid.add(resultImage, x, y);
                grid.add(txtNode, x, y);
            }
        }



    }

}
