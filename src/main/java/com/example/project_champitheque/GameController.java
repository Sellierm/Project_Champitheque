package com.example.project_champitheque;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameController implements Quit {

    GameModel model;

    @FXML
    private Button quit;//boutton pour quitter le jeu

    @FXML
    private Button help;//boutton pour quitter le jeu

    public GridPane grid;

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


    public void initialize(){
        model = new GameModel(15, 10);
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
            }
        }

        grid.setHgap(30);
        grid.setVgap(30);

        for(Node node : grid.getChildren()){
            node.setOnMouseReleased(e -> {
                Text target = (Text) e.getTarget();
                System.out.println(target.textProperty().get());
                System.out.println(target.getUserData());
            });
        }

        System.out.println(grid);
        System.out.println(grid.getChildren());
    }


}
