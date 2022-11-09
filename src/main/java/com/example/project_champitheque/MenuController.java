package com.example.project_champitheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class MenuController {

    @FXML
    private Text Pseudo;

    @FXML
    private Text level;

    @FXML
    private Button Game_1;//boutton pour lancer jeu

    public void handleGame1() throws IOException {//fonction pour changer de jeu, ici du menu de base au menu moche de corentin
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("menu.fxml"));//deuième fichier fxml
        Stage window =(Stage) Game_1.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }
}