package com.example.project_champitheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController implements Quit {

    @FXML
    private Button quit;//boutton pour quitter le jeu

    @FXML
    private Button help;//boutton pour quitter le jeu

    @Override
    public void Quit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) quit.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }


    public void Help() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) help.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
    }


}
