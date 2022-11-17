package com.example.project_champitheque;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
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
    private VBox existingAccounts;

    @FXML
    private Button play;

    public void newPlayer() throws IOException {
        String name = newName.getText();
        model.setNewPlayer(name);
        goToMenu();
    }

    public void choosePlayer(int playerId) throws IOException {
        model.setParamPlayer(playerId);
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
        List<String> players = model.getAllData();
        List<Button> containerPlayerslist = new ArrayList<>();
        String stringPlayers = "";
        int compt = 0;
        for(String i : players){

            compt++;
            if(compt%3 == 2)stringPlayers+="Niveau :   ";
            if(compt%3 == 0)stringPlayers+="Points :   ";
            stringPlayers+=i+"\n";

            if(compt%3 == 0) {
                Button container = new Button();

                container.setPrefWidth(180);
                container.setPrefHeight(90);
                container.setCursor(Cursor.HAND);
                container.setTextAlignment(TextAlignment.CENTER);
                VBox.setVgrow(container, Priority.ALWAYS);
                VBox.setMargin(container,new Insets(10,20,0,20));
                container.setStyle("-fx-background-color : #a25e26; -fx-background-radius : 30; -fx-effect: dropShadow(three-pass-box, rgba(0,0,0,0.5), 3.0, 0.0, 0.0, 3.0); -fx-font-size: 16px; -fx-text-fill: white;");

                //container.setLayoutY(400*((compt-3)/3));
                int id = compt/3;
                container.setUserData(id);
                container.setOnAction(event -> {
                    try {
                        choosePlayer((Integer)container.getUserData());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                container.textProperty().set(stringPlayers);
                containerPlayerslist.add(container);
                stringPlayers = "";
            }
        }
        existingAccounts.getChildren().addAll(containerPlayerslist);
    }
}
