package com.example.project_champitheque;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class GameTestController implements Quit, Help, NewGame, PopUpEnd{

    GameTestModel model;

    @FXML
    private Button quit;

    @FXML
    private Button help;

    @FXML
    private Button newGame;

    @FXML
    private Pane popupend;

    @FXML
    private Text finalScore;

    @FXML
    private Text lvlEarned;

    @FXML
    private Pane popuphelp;



    @FXML
    private Pane area;

    List<ImageView> arrayFallingItems;

    @Override
    public void Quit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) quit.getScene().getWindow();
        window.setScene(new Scene(fxmlLoader.load()));
        window.setTitle("Champithèque");
    }


    public void Help(){
        popuphelp.setVisible(true);
    }

    public void CloseHelp(){
        popuphelp.setVisible(false);
    }


    public void NewGame() {

    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        String findTxt = "";
        finalScore.setText(findTxt);
        lvlEarned.setText(scoreTxt);
        popupend.setVisible(true);

    }
    public void ShowPopUpEnd(String winner, int score){
        popupend.setVisible(true);
    }

    public void ClosePopUpEnd(){
        popupend.setVisible(false);

    }


    public void initialize(){
        model = new GameTestModel();
        arrayFallingItems = new ArrayList<>();
        addFallingItems();

    }

    public void addFallingItems(){
        for(int i = 0; i < 100; i++){
            arrayFallingItems.add(new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.jpg"))));
            arrayFallingItems.get(i).setLayoutY(50);

            Random rand = new Random();
            int minLayoutX = 0;
            int maxLayoutX = 726;
            int randLayoutX = rand.nextInt(maxLayoutX - minLayoutX + 1) + minLayoutX;
            int minLayoutY = 0;
            int maxLayoutY = 509;
            int randLayoutY = rand.nextInt(maxLayoutY - minLayoutY + 1) + minLayoutY;
            arrayFallingItems.get(i).setLayoutX(randLayoutX);
            arrayFallingItems.get(i).setLayoutY(randLayoutY);
        }
    }



    public void addPeriodiqueItems(){
        System.out.println("100 périodes écoulées on ajoute des champignons");
        addFallingItems();
        //area.getChildren().clear();
        area.getChildren().addAll(arrayFallingItems);
    }




    public void start(){
        area.getChildren().addAll(arrayFallingItems);
        final int[] compteur = {0};
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                playChampis();
            }
        },0,100);
    }


    public void playChampis(){
        Random rand = new Random();
        if(arrayFallingItems.size() != 0){
            for(ImageView img : arrayFallingItems){
                int minmove = -3;
                int maxmove = 3;
                int move = rand.nextInt(maxmove - minmove + 1) + minmove;
                img.setLayoutX(img.getLayoutX() + move);
                img.setLayoutY(img.getLayoutY() + move);
            }
            int minIndex = 0;
            int maxIndex = arrayFallingItems.size()-1;
            int index = rand.nextInt(maxIndex - minIndex + 1) + minIndex;
            arrayFallingItems.get(index).setScaleX(1.5);
            arrayFallingItems.get(index).setScaleY(1.5);

            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
                arrayFallingItems.get(index).setScaleX(1);
                arrayFallingItems.get(index).setScaleY(1);
            });
        }
    }
}
