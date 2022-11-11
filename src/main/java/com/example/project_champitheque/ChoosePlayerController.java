package com.example.project_champitheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChoosePlayerController {

    ChoosePlayerModel tmp;

    @FXML
    private Text newName;   // Zone de texte où est inscrit le nouveau pseudo

    @FXML
    private Text existingAccounts;

    @FXML
    private Button play;

    public void newPlayer()  {

    }

    public void goToMenu() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));//deuième fichier fxml
        Stage window =(Stage) play.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }

    public void initialize(){
        tmp = new ChoosePlayerModel();
        List<String> players = tmp.getAllData();
        String stringPlayers = "";
        for(String i : players){
            stringPlayers+=i+"\n";
        }
        existingAccounts.setText(stringPlayers);
        // IL NOUS FAUT MAINTENANT ECRIRE UNE FONCTION QUI ECRIT LE NOUVEAU PSEUDO DANS UN FICHIER TEXTE
        //   + UN CHANGEMENT DE SCENE
    }
}
