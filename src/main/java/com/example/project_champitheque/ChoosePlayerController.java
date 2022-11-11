package com.example.project_champitheque;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.*;

import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ChoosePlayerController {

    ChoosePlayerModel tmp;

    @FXML
    private Text newName;   // Zone de texte où est inscrit le nouveau pseudo

    @FXML
    private Pane existingAccounts;

    @FXML
    private Button play;

    public void newPlayer()  {

    }

    public void choosePlayer(int playerId) throws IOException {
        tmp.setParamPlayer(playerId);
        goToMenu();
    }

    public void goToMenu() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));//deuième fichier fxml
        Stage window =(Stage) play.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }

    public void initialize(){
        tmp = new ChoosePlayerModel();
        List<String> players = tmp.getAllData();
        List<Button> containerPlayerslist = new ArrayList<>();
        String stringPlayers = "";
        int compt = 0;
        for(String i : players){

            compt++;
            stringPlayers+=i+"\n";

            if(compt%3 == 0) {
                Button container = new Button();
                container.setMinWidth(200);
                container.setMinHeight(100);
                container.setLayoutY(200*((compt-3)/3));
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
        // IL NOUS FAUT MAINTENANT ECRIRE UNE FONCTION QUI ECRIT LE NOUVEAU PSEUDO DANS UN FICHIER TEXTE
        //   + UN CHANGEMENT DE SCENE
    }
}
