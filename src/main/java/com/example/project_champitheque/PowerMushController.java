package com.example.project_champitheque;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class PowerMushController implements Quit, Help, NewGame, PopUpEnd {

    PowerMushModel model;

    @FXML
    private Button quit;

    @FXML
    private Button help;

    @FXML
    private Button newGame;

    @FXML
    private Pane popupend;

    @FXML
    private Text winnerNode;

    @FXML
    private Text scoreNode;


    @FXML
    private Pane popuphelp;



    public GridPane grid;





    @FXML
    public ImageView panier;

    public boolean isPanierActive = false;



    @FXML
    private ImageView diff1;

    @FXML
    private ImageView diff2;

    @FXML
    private ImageView diff3;

    public List<ImageView> tabDiff = new ArrayList<ImageView>();



    @FXML
    private ImageView playerIcone;

    @FXML
    private ImageView robotIcone;

    public List<ImageView> tabIcone = new ArrayList<ImageView>();


    public int joueurCourant = 0;
    public int joueur1 = 1;
    public int joueur2 = -1;




    @Override
    public void Quit() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("Menu.fxml"));
        Stage window =(Stage) quit.getScene().getWindow();
        window.setMinWidth(1000);
        window.setMinHeight(600);
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
        model.restartGame(1, joueur1, joueur2);
        setGrilleFX();
        ClosePopUpEnd();
    }


    public void ShowPopUpEnd(int score){
        popupend.setVisible(true);
    }
    public void ShowPopUpEnd(String winner, int score){
        String scoreTxt = ""+score;
        winnerNode.setText(winner);
        scoreNode.setText(scoreTxt);
        popupend.setVisible(true);
    }

    public void ClosePopUpEnd(){
        popupend.setVisible(false);

    }





    public void initialize(){

        model = new PowerMushModel(joueur1, joueur2);

        setGrilleFX();




        //Set difficulty
        diff1.setScaleX(1.3);
        diff1.setScaleY(1.3);
        diff1.setUserData("1");
        diff2.setUserData("2");
        diff3.setUserData("3");
        this.tabDiff.add(diff1);
        this.tabDiff.add(diff2);
        this.tabDiff.add(diff3);



        //Set difficulty

        robotIcone.setScaleX(1.3);
        robotIcone.setScaleY(1.3);
        playerIcone.setUserData("player");
        robotIcone.setUserData("robot");
        this.tabIcone.add(robotIcone);
        this.tabIcone.add(playerIcone);
    }


    public void setGrilleFX(){
        joueurCourant = model.getJoueurCourant();

        setCursor();
        updateGrid();


        //On (ré)active le panier
        isPanierActive = false;
        panier.setOpacity(1);

    }


    //Fonction appelée au click sur une case pour afficher le résultat de la case
    public void clickedCase(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        System.out.println("Colone : " + target.getUserData());

        //On récupère les datas de la case ciblée pour connaitre les coordonnées
        String data = (String) target.getUserData();
        int x = Integer.parseInt(data);
        play(x);
    }


    public void play(int x){
        int resultPartie = 0;
        if(isPanierActive && model.ablePanier()){
            model.playPanier(x);
            isPanierActive = false;
            panier.setScaleX(1);
            panier.setScaleY(1);
            if(joueur2 == -1)panier.setOpacity(0.5);
            grid.setCursor(Cursor.DEFAULT);
        }
        else {
            resultPartie = model.play(x, joueurCourant);
            if(resultPartie == 1){
                ShowPopUpEnd("RedMush", model.getScore());
            }
            else if (resultPartie == -1) {
                ShowPopUpEnd("MushBot", model.getScore());
            }
            else if (resultPartie == 2) {
                ShowPopUpEnd("YellowMush", model.getScore());
            }
            else if (resultPartie == 3){
                ShowPopUpEnd("Aucun", model.getScore());
            }
            if(resultPartie != 0){
                showRowWin(model.getListCooChampiWin());
            }
        }
        updateGrid();

        if(resultPartie != 0){
            showRowWin(model.getListCooChampiWin());
        }

        joueurCourant = model.getJoueurCourant();
        setCursor();

        if(joueurCourant == -1 && !model.isGameEnd()){
            System.out.println("Au tour du robot");
            //CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> {
                play(0);
            //});
        }
    }

    public void setCursor(){
        if(joueurCourant == 1){
            Image image = new Image(Application.class.getResourceAsStream("/img/redCursor.png"));
            grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
        }
        if(joueurCourant == -1){
            grid.setCursor(Cursor.DEFAULT);
        }
        if(joueurCourant == 2){
            Image image = new Image(Application.class.getResourceAsStream("/img/yellowCursor.png"));
            grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
        }
    }

    public void setPlayer(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        String data = (String) target.getUserData();
        if(data.equals("player")){
            joueur2 = 2;
            for (ImageView img : this.tabIcone) {
                img.setScaleX(1);
                img.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
        }
        if(data.equals("robot")){
            joueur2 = -1;
            for (ImageView img : this.tabIcone) {
                img.setScaleX(1);
                img.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
        }
        System.out.println("Changement de joueur"+this.joueur2);
    }

    public void updateGrid(){

        grid.getChildren().clear();
        ArrayList<ArrayList> grille = model.getGrille();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = i;
                int y = j%grille.get(i).size();
                String txt = x+"";

                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/gridPane.png")));
                selectedImage.setUserData(txt);
                grid.add(selectedImage, x, y);

                if((int)grille.get(x).get(y) == 1){
                    ImageView pionImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/testred.png")));
                    pionImage.setUserData(txt);
                    grid.add(pionImage, x, y);

                }
                if((int)grille.get(x).get(y) == -1 || (int)grille.get(x).get(y) == 2){
                    ImageView pionImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/testyellow.png")));
                    pionImage.setUserData(txt);
                    grid.add(pionImage, x, y);

                }
            }
        }

        for(Node node : grid.getChildren()){
            node.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    clickedCase(e);

                }
                /*if(e.getButton() == MouseButton.SECONDARY){
                    placeBarriere(e);
                }*/
            });
        }

    }


    public void showRowWin(List<Integer[]> listeChampis){
        System.out.println("On affiche le section gagnante");
        for(Integer[] champi : listeChampis){
            System.out.println(champi[0]+ ", " + champi[1]);
            ImageView circleImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/circleEnd.png")));
            grid.add(circleImage, champi[0], champi[1]);
        }
    }



    public void setDifficulty(MouseEvent event){
        ImageView target = (ImageView) event.getTarget();
        String data = (String) target.getUserData();
        int valueData = Integer.parseInt(data);
        if(model.setDifficulty(valueData)) {
            for (ImageView img : this.tabDiff) {
                img.setScaleX(1);
                img.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
        }
    }


    public void usePanier(){
        if(model.ablePanier()) {
            if (isPanierActive) {
                panier.setScaleX(1);
                panier.setScaleY(1);

                grid.setCursor(Cursor.DEFAULT);
            } else {
                panier.setScaleX(1.5);
                panier.setScaleY(1.5);

                Image image = new Image(Application.class.getResourceAsStream("/img/panier.png"));
                grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));

            }
            isPanierActive = !isPanierActive;
            System.out.println("Panier actif");
        }
        System.out.println("Panier unable");
    }

}
