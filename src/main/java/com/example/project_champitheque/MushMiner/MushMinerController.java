package com.example.project_champitheque.MushMiner;

import com.example.project_champitheque.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.List;

public class MushMinerController extends GameController {

    MushMinerModel model;


    @FXML
    private GridPane grid;

    @FXML
    private Text champiFind;

    @FXML
    private Text restant;

    @FXML
    private Text champiRestant;

    @FXML
    private TextField inputSizeX;

    @FXML
    private TextField inputSizeY;




    @FXML
    public ImageView loupe;

    public boolean isLoupeActive = false;


    @FXML
    public ImageView manure;

    public boolean isManureActive = false;




    //CheatCode
    @FXML
    private Label labelRestants;



    public void NewGame() {
        if(Integer.parseInt(inputSizeX.getText()) >19)inputSizeX.setText("19");
        if(Integer.parseInt(inputSizeX.getText()) <7)inputSizeX.setText("7");
        if(Integer.parseInt(inputSizeY.getText()) >13)inputSizeY.setText("13");
        if(Integer.parseInt(inputSizeY.getText()) <10)inputSizeY.setText("10");
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model.resetGame(sizeX, sizeY);
        setGrilleFX();
        ClosePopUpEnd();

        //On (ré)active la loupe
        isLoupeActive = false;
        loupe.setOpacity(1);

        //On (ré)active l'engrais
        isManureActive = false;
        manure.setOpacity(1);
    }

    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        String findTxt = ""+model.scoreChampiProperty().get();
        scoreNode2.setText(findTxt);
        scoreNode1.setText(scoreTxt);
        popupend.setVisible(true);

    }

    public String getFileToReadStats(){
        return "MushMinerScores";
    }
    public void setDifficulty(MouseEvent event){
        ImageView target = (ImageView) event.getTarget();
        String data = (String) target.getUserData();
        int valueData = Integer.parseInt(data);
        for(ImageView img : this.tabDiff){
            img.setScaleX(1);
            img.setScaleY(1);
        }
        target.setScaleX(1.3);
        target.setScaleY(1.3);
        model.setDifficulty(valueData);
    }




    public void initialize(){
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model = new MushMinerModel(sizeX, sizeY);

        champiFind.textProperty().bind(model.scoreChampiProperty().asString());
        restant.textProperty().bind(model.coupsRestantsProperty().asString());
        champiRestant.textProperty().bind(model.champiRestantsProperty().asString());

        setGrilleFX();


        //On empêche les input de caractères autres que des nombres
        inputSizeX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputSizeX.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        inputSizeY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputSizeY.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        initializeDifficulty();


        //CheatCode pour faire varier le nombre de coups restants
        labelRestants.setOnMouseReleased(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                model.increaseRestant(true);

            }
            if(e.getButton() == MouseButton.SECONDARY){
                model.increaseRestant(false);
            }
        });
    }


    public void setGrilleFX(){
        grid.getChildren().clear();
        List<List<BoxMushMinerToDisplay>> grille = model.getGrilleToDisplay();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                String txt = x+"-"+y;

                BoxMushMinerToDisplay caseValue = grille.get(y).get(x);
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));


                if(caseValue.value == BoxValueMushMiner.DEFAULT1) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));
                    selectedImage.setOnMouseReleased(e -> {
                        if(e.getButton() == MouseButton.PRIMARY){
                            clickedCase(e);

                        }
                        if(e.getButton() == MouseButton.SECONDARY){
                            placeBarriere(e);
                        }
                    });
                }
                if(caseValue.value == BoxValueMushMiner.DEFAULT2) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe2.png")));
                    selectedImage.setOnMouseReleased(e -> {
                        if(e.getButton() == MouseButton.PRIMARY){
                            clickedCase(e);

                        }
                        if(e.getButton() == MouseButton.SECONDARY){
                            placeBarriere(e);
                        }
                    });
                }
                if(caseValue.value == BoxValueMushMiner.DEFAULT3) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe3.png")));
                    selectedImage.setOnMouseReleased(e -> {
                        if(e.getButton() == MouseButton.PRIMARY){
                            clickedCase(e);

                        }
                        if(e.getButton() == MouseButton.SECONDARY){
                            placeBarriere(e);
                        }
                    });
                }
                if(caseValue.value == BoxValueMushMiner.DEFAULT4) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe4.png")));
                    selectedImage.setOnMouseReleased(e -> {
                        if(e.getButton() == MouseButton.PRIMARY){
                            clickedCase(e);

                        }
                        if(e.getButton() == MouseButton.SECONDARY){
                            placeBarriere(e);
                        }
                    });
                }
                if(caseValue.value == BoxValueMushMiner.VIDE) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/feuille.png")));
                }
                if(caseValue.value == BoxValueMushMiner.CHAMPI) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.png")));
                }
                if(caseValue.value == BoxValueMushMiner.JACKPOT) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/megaChampi.png")));
                }
                if(caseValue.value == BoxValueMushMiner.BAD) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/badChampi.png")));
                }
                selectedImage.setUserData(txt);

                grid.add(selectedImage, x, y);


                if(caseValue.value == BoxValueMushMiner.VIDE) {
                    Text txtNode = new Text(String.valueOf(caseValue.champiAutour));
                    txtNode.setStyle("-fx-font: 30 arial;");
                    txtNode.setFill(Color.WHITE);
                    txtNode.setWrappingWidth(40);
                    txtNode.setTextAlignment(TextAlignment.CENTER);
                    grid.add(txtNode, x, y);
                }

                if(caseValue.locked) {
                    ImageView barriere = new ImageView(new Image(Application.class.getResourceAsStream("/img/barriere.png")));
                    grid.add(barriere, x, y);
                }
            }
        }

    }


    public void revealGrid(){
        grid.getChildren().clear();
        List<List<BoxMushMinerToDisplay>> grille = model.getGrilleEnd();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                String txt = x+"-"+y;
                Text txtNode = new Text(txt);
                txtNode.setUserData(txt);
                txtNode.setWrappingWidth(40);
                txtNode.setTextAlignment(TextAlignment.CENTER);
                grid.add(txtNode, x, y);

                BoxMushMinerToDisplay caseValue = grille.get(y).get(x);
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));

                if(caseValue.value == BoxValueMushMiner.VIDE) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/feuille.png")));
                }
                if(caseValue.value == BoxValueMushMiner.CHAMPI) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.png")));
                }
                if(caseValue.value == BoxValueMushMiner.JACKPOT) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/megaChampi.png")));
                }
                if(caseValue.value == BoxValueMushMiner.BAD) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/badChampi.png")));
                }
                selectedImage.setUserData(txt);

                grid.add(selectedImage, x, y);

                if(caseValue.locked) {
                    ImageView barriere = new ImageView(new Image(Application.class.getResourceAsStream("/img/barriere.png")));
                    grid.add(barriere, x, y);
                }
            }
        }
    }




    public void clickedCase(MouseEvent e){
        if(!model.isGameEnd()) {
            Node target = (Node) e.getTarget();
            System.out.println(target.getUserData());

            //On récupère les datas de la case ciblée pour connaitre les coordonnées
            String data = (String) target.getUserData();
            String[] arr = null;
            arr = data.split("-");
            List<String> list = Arrays.asList(arr);
            int x = Integer.parseInt(list.get(0));
            int y= Integer.parseInt(list.get(1));

            //On verifie si la loupe à été activée
            if(isLoupeActive){
                model.playLoupe(x, y);
                isLoupeActive = false;
                loupe.setScaleX(1);
                loupe.setScaleY(1);
                loupe.setOpacity(0.5);
                grid.setCursor(Cursor.DEFAULT);
            }

            //On verifie si l'engrais à été activée
            if(isManureActive){
                model.playEngrais(x, y);
                isManureActive = false;
                manure.setScaleX(1);
                manure.setScaleY(1);
                manure.setOpacity(0.5);
                grid.setCursor(Cursor.DEFAULT);
            }

            model.play(x, y);

            if(!model.isGameEnd()) {
                setGrilleFX();
                System.out.println("On réaffiche tout");
            }
            else {
                revealGrid();
                ShowPopUpEnd(model.getScore());
                System.out.println("On affiche la grille de fin");
            }

        }
        else{
            revealGrid();
            System.out.println("On affiche la grille de fin");
        }
    }

    public void placeBarriere(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        System.out.println(target.getUserData());

        String data = (String) target.getUserData();
        String[] arr = null;
        arr = data.split("-");
        List<String> list = Arrays.asList(arr);
        int x = Integer.parseInt(list.get(0));
        int y= Integer.parseInt(list.get(1));

        model.lockCase(x, y);

        setGrilleFX();
    }


    public void useLoupe(){
        if(model.loupeAvaible()) {
            if (isLoupeActive) {
                loupe.setScaleX(1);
                loupe.setScaleY(1);

                grid.setCursor(Cursor.DEFAULT);
            } else {
                loupe.setScaleX(1.5);
                loupe.setScaleY(1.5);

                Image image = new Image(Application.class.getResourceAsStream("/img/loupeCursor.png"));
                grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));

            }
            isLoupeActive = !isLoupeActive;
            System.out.println("Loupe active");
        }
    }


    public void useManure(){
        if(model.engraisAvaible()) {
            if (isManureActive) {
                manure.setScaleX(1);
                manure.setScaleY(1);

                grid.setCursor(Cursor.DEFAULT);
            } else {
                manure.setScaleX(1.5);
                manure.setScaleY(1.5);

                Image image = new Image(Application.class.getResourceAsStream("/img/manure.png"));
                grid.setCursor(new ImageCursor(image,image.getWidth() / 2,image.getHeight() /2));

            }
            isManureActive = !isManureActive;
            System.out.println("Engrais actif");
        }
    }
}
