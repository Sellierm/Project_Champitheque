package com.example.project_champitheque.MushMiner;

import com.example.project_champitheque.*;
import com.example.project_champitheque.Interfaces.*;
import com.example.project_champitheque.fileManager.Read;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MushMinerController implements Quit, Help, NewGame, PopUpEnd, StatsGame {

    MushMinerModel model;

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
    private Pane ranking;
    @FXML
    private VBox containerRanking;


    @FXML
    private Pane popuphelp;


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
    private ImageView diff1;

    @FXML
    private ImageView diff2;

    @FXML
    private ImageView diff3;

    public List<ImageView> tabDiff = new ArrayList<ImageView>();



    @FXML
    public ImageView loupe;

    public boolean isLoupeActive = false;


    @FXML
    public ImageView manure;

    public boolean isManureActive = false;




    //CheatCode
    @FXML
    private Label labelRestants;

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

    public void ShowStats(){
        ranking.setVisible(true);
        Read reader = new Read();
        List<List<String>> listRanking = reader.readAllFromFile("MushMinerScores");
        System.out.println(listRanking);
        listRanking.sort((elem1, elem2) -> Integer.parseInt(elem2.get(1)) - Integer.parseInt(elem1.get(1)));
        containerRanking.getChildren().clear();
        for(int i = 0; i < listRanking.size() && i < 10; i++){
            List<String> eachRanking = listRanking.get(i);
            String name = reader.getName(Integer.parseInt(eachRanking.get(0)));
            Label nodeLine = new Label();
            nodeLine.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
            nodeLine.setText(name+" : "+eachRanking.get(1)+" points");
            containerRanking.getChildren().add(nodeLine);

        }
    }

    public void CloseStats(){
        ranking.setVisible(false);
        containerRanking.getChildren().clear();
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

        //Set difficulty

        diff1.setScaleX(1.3);
        diff1.setScaleY(1.3);
        diff1.setUserData("1");
        diff2.setUserData("2");
        diff3.setUserData("3");
        this.tabDiff.add(diff1);
        this.tabDiff.add(diff2);
        this.tabDiff.add(diff3);


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
        List<List<CaseToDisplay>> grille = model.getGrilleToDisplay();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                String txt = x+"-"+y;

                CaseToDisplay caseValue = grille.get(y).get(x);
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));


                if(caseValue.value == ValueCase.DEFAULT1) {
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
                if(caseValue.value == ValueCase.DEFAULT2) {
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
                if(caseValue.value == ValueCase.DEFAULT3) {
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
                if(caseValue.value == ValueCase.DEFAULT4) {
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
                if(caseValue.value == ValueCase.VIDE) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/feuille.png")));
                }
                if(caseValue.value == ValueCase.CHAMPI) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.png")));
                }
                if(caseValue.value == ValueCase.JACKPOT) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/megaChampi.png")));
                }
                if(caseValue.value == ValueCase.BAD) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/badChampi.png")));
                }
                selectedImage.setUserData(txt);

                grid.add(selectedImage, x, y);


                if(caseValue.value == ValueCase.VIDE) {
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

        /*for(Node node : grid.getChildren()){
            node.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    clickedCase(e);

                }
                if(e.getButton() == MouseButton.SECONDARY){
                    placeBarriere(e);
                }
            });
        }*/

    }


    public void revealGrid(){
        grid.getChildren().clear();
        List<List<CaseToDisplay>> grille = model.getGrilleEnd();
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

                CaseToDisplay caseValue = grille.get(y).get(x);
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));

                if(caseValue.value == ValueCase.VIDE) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/feuille.png")));
                }
                if(caseValue.value == ValueCase.CHAMPI) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.png")));
                }
                if(caseValue.value == ValueCase.JACKPOT) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/megaChampi.png")));
                }
                if(caseValue.value == ValueCase.BAD) {
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
