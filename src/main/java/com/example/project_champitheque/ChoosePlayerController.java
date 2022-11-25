package com.example.project_champitheque;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.event.*;

import java.awt.*;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class ChoosePlayerController {

    ChoosePlayerModel model;

    @FXML
    private TextField newName;   // Zone de texte où est inscrit le nouveau pseudo
    @FXML
    private ColorPicker color;

    @FXML
    private VBox existingAccounts;

    @FXML
    private Button play;

    public void newPlayer() throws IOException {
        String name = newName.getText();
        if(name != "" && name != " ") {
            Color colorValue = color.getValue();
            model.setNewPlayer(name, toHexString(colorValue));
            goToMenu();
        }
    }

    public void choosePlayer(int playerId) throws IOException {
        model.setParamPlayer(playerId);
        System.out.println("Choose : "+playerId);
        goToMenu();
    }

    public void goToMenu() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));//deuième fichier fxml
        Stage window =(Stage) play.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(600);
        window.setScene(new Scene(fxmlLoader.load()));
    }

    public void initialize(){
        model = new ChoosePlayerModel();
        List<List<String>> players = model.getAllData();
        List<Button> containerPlayerslist = new ArrayList<>();

        int index = 0;
        for(List<String> i : players){

            String stringPlayers = i.get(0)+"\n Score : "+i.get(1)+" pts";

            Button container = new Button();

            container.setPrefWidth(180);
            container.setPrefHeight(90);
            container.setCursor(Cursor.HAND);
            container.setTextAlignment(TextAlignment.CENTER);
            VBox.setVgrow(container, Priority.ALWAYS);
            VBox.setMargin(container,new Insets(10,20,0,20));
            container.setStyle("-fx-background-color : #a25e26; -fx-background-radius : 30; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.5), 3.0, 0.0, 0.0, 3.0); -fx-font-size: 16px; -fx-text-fill: white;");


            container.setUserData(index);
            container.setOnAction(event -> {
                try {
                    Node target = (Node)event.getTarget();
                    int idPlayer = (int)target.getUserData();
                    System.out.println("Choose : "+idPlayer);
                    choosePlayer(idPlayer);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            container.textProperty().set(stringPlayers);
            containerPlayerslist.add(container);

            index++;
        }
        existingAccounts.getChildren().addAll(containerPlayerslist);
    }


    private static String toHexString(Color color) {
        int r = ((int) Math.round(color.getRed()     * 255)) << 24;
        int g = ((int) Math.round(color.getGreen()   * 255)) << 16;
        int b = ((int) Math.round(color.getBlue()    * 255)) << 8;
        int a = ((int) Math.round(color.getOpacity() * 255));
        return String.format("#%08X", (r + g + b + a));
    }
}
