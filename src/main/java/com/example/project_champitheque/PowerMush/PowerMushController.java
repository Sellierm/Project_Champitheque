package com.example.project_champitheque.PowerMush;

import com.example.project_champitheque.*;
import com.example.project_champitheque.Interfaces.Help;
import com.example.project_champitheque.Interfaces.NewGame;
import com.example.project_champitheque.Interfaces.PopUpEnd;
import com.example.project_champitheque.Interfaces.Quit;
import com.example.project_champitheque.fileManager.Read;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PowerMushController extends GameController {

    PowerMushModel model;


    public GridPane grid;




    @FXML
    public ImageView panier;

    public boolean isPanierActive = false;

    @FXML
    public ImageView boots;

    public boolean isBootsActive = false;



    @FXML
    private ImageView playerIcone;

    @FXML
    private ImageView robotIcone;

    public List<ImageView> tabIcone = new ArrayList<ImageView>();


    public Joueur joueurCourant = Joueur.AUCUN;
    public Joueur joueur1 = Joueur.JOUEUR1;
    public Joueur joueur2 = Joueur.BOT;



    public void NewGame() {
        model.restartGame(joueur1, joueur2);
        setGrilleFX();
        ClosePopUpEnd();
    }

    public void ShowPopUpEnd(Joueur winner, int score){
        String scoreTxt = ""+score;
        String winnerText = "Aucun";
        if(winner == Joueur.JOUEUR1)winnerText = "RedMush";
        if(winner == Joueur.JOUEUR2)winnerText = "YellowMush";
        if(winner == Joueur.BOT)winnerText = "MushBot";
        scoreNode2.setText(winnerText);
        scoreNode1.setText(scoreTxt);
        popupend.setVisible(true);
    }

    public String getFileToReadStats(){
        return "PowerMushScores";
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





    public void initialize(){

        model = new PowerMushModel(joueur1, joueur2);

        setGrilleFX();

        initializeDifficulty();

        //Set adversaire
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

        isBootsActive = false;
        boots.setOpacity(1);

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
        if(!model.isGameEnd()) {
            if (isPanierActive && model.ablePanier()) {
                model.playPanier(x);
                isPanierActive = false;
                panier.setScaleX(1);
                panier.setScaleY(1);
                if (joueur2 == Joueur.BOT) panier.setOpacity(0.5);
                grid.setCursor(Cursor.DEFAULT);
                if (model.isGameEnd()) {
                    showWin(model.getListCooChampiWin());
                    ShowPopUpEnd(model.getWinner(), model.getScore());
                }
            } else if (isBootsActive && model.ableBoots()) {
                model.playBoots(x);
                isBootsActive = false;
                boots.setScaleX(1);
                boots.setScaleY(1);
                if (joueur2 == Joueur.BOT) boots.setOpacity(0.5);
                grid.setCursor(Cursor.DEFAULT);
                if (model.isGameEnd()) {
                    showWin(model.getListCooChampiWin());
                    ShowPopUpEnd(model.getWinner(), model.getScore());
                }
            } else {
                model.play(x, joueurCourant);
            }

            joueurCourant = model.getJoueurCourant();
            setCursor();
            updateGrid();

            if (model.isGameEnd()) {
                showWin(model.getListCooChampiWin());
                ShowPopUpEnd(model.getWinner(), model.getScore());
            }

            if (joueurCourant == Joueur.BOT && !model.isGameEnd()) {
                play(0);
            }
        }
        else {
            updateGrid();
            showWin(model.getListCooChampiWin());
        }
    }


    public void updateGrid(){

        grid.getChildren().clear();
        ArrayList<ArrayList<Joueur>> grille = model.getGrilleToDisplay();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = i;
                int y = j%grille.get(i).size();
                String txt = x+"";

                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/gridPane.png")));
                selectedImage.setUserData(txt);
                grid.add(selectedImage, x, y);

                if(grille.get(x).get(y) == Joueur.JOUEUR1){
                    ImageView pionImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/testred.png")));
                    pionImage.setUserData(txt);
                    grid.add(pionImage, x, y);

                }
                if(grille.get(x).get(y) == Joueur.BOT || grille.get(x).get(y) == Joueur.JOUEUR2){
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
            });
        }

    }
    public void showWin(List<Integer[]> listeChampis){
        System.out.println("On affiche le section gagnante");
        for(Integer[] champi : listeChampis){
            System.out.println(champi[0]+ ", " + champi[1]);
            ImageView circleImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/circleEnd.png")));
            grid.add(circleImage, champi[0], champi[1]);
        }
    }



    //Fonction utilitaires
    public void setCursor(){
        if(joueurCourant == Joueur.JOUEUR1){
            Image image = new Image(Application.class.getResourceAsStream("/img/redCursor.png"));
            grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
        }
        if(joueurCourant == Joueur.BOT){
            grid.setCursor(Cursor.DEFAULT);
        }
        if(joueurCourant == Joueur.JOUEUR2){
            Image image = new Image(Application.class.getResourceAsStream("/img/yellowCursor.png"));
            grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));
        }
    }

    public void setPlayer(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        String data = (String) target.getUserData();
        if(data.equals("player")){
            joueur2 = Joueur.JOUEUR2;
            for (ImageView img : this.tabIcone) {
                img.setScaleX(1);
                img.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
        }
        if(data.equals("robot")){
            joueur2 = Joueur.BOT;
            for (ImageView img : this.tabIcone) {
                img.setScaleX(1);
                img.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
        }
        System.out.println("Changement de joueur"+this.joueur2);
    }


    //Bonus
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


    public void useBoots(){
        if(model.ableBoots()) {
            if (isBootsActive) {
                boots.setScaleX(1);
                boots.setScaleY(1);

                grid.setCursor(Cursor.DEFAULT);
            } else {
                boots.setScaleX(1.5);
                boots.setScaleY(1.5);

                Image image = new Image(Application.class.getResourceAsStream("/img/boots.png"));
                grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));

            }
            isBootsActive = !isBootsActive;
            System.out.println("Panier actif");
        }
        System.out.println("Panier unable");
    }

}
