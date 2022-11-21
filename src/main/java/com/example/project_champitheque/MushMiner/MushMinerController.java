package com.example.project_champitheque.MushMiner;

import com.example.project_champitheque.*;
import com.example.project_champitheque.Interfaces.Help;
import com.example.project_champitheque.Interfaces.NewGame;
import com.example.project_champitheque.Interfaces.PopUpEnd;
import com.example.project_champitheque.Interfaces.Quit;
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

public class MushMinerController implements Quit, Help, NewGame, PopUpEnd {

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
    private Pane popuphelp;



    public GridPane grid;

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

    public boolean isLoupeUsed = false;


    @FXML
    public ImageView manure;

    public boolean isManureActive = false;

    public boolean isManureUsed = false;




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
        if(Integer.parseInt(inputSizeX.getText()) <10)inputSizeX.setText("10");
        if(Integer.parseInt(inputSizeY.getText()) >13)inputSizeY.setText("13");
        if(Integer.parseInt(inputSizeY.getText()) <10)inputSizeY.setText("10");
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model.restartGame(sizeX, sizeY);
        setGrilleFX();
        ClosePopUpEnd();
    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        String findTxt = ""+model.getChampiFind();
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
        int sizeX = Integer.parseInt(inputSizeX.getText());
        int sizeY = Integer.parseInt(inputSizeY.getText());
        model = new MushMinerModel(sizeX, sizeY);

        champiFind.textProperty().bind(model.champiFindProperty().asString());
        restant.textProperty().bind(model.restantProperty().asString());
        champiRestant.textProperty().bind(model.champiRestantProperty().asString());

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
        ArrayList<ArrayList> grille = model.getGrille();
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
                ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe.png")));
                if(Math.random() <= 0.2){
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe2.png")));
                } else if (Math.random() <= 0.4) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe3.png")));
                } else if (Math.random() <= 0.6) {
                    selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/herbe4.png")));
                }
                selectedImage.setUserData(txt);
                grid.add(selectedImage, x, y);
            }
        }

        for(Node node : grid.getChildren()){
            node.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    clickedCase(e);

                }
                if(e.getButton() == MouseButton.SECONDARY){
                    placeBarriere(e);
                }
            });
        }


        //On (ré)active la loupe
        isLoupeActive = false;
        isLoupeUsed = false;
        loupe.setOpacity(1);

        //On (ré)active la loupe
        isManureActive = false;
        isManureUsed = false;
        manure.setOpacity(1);

    }


    //Fonction appelée au click sur une case pour afficher le résultat de la case
    public void clickedCase(MouseEvent e){
        if(model.getRestant() > 0 && model.getChampiRestant() > 0 && !model.isGameEnd()) {


            ImageView target = (ImageView) e.getTarget();
            System.out.println(target.getUserData());

            //On récupère les datas de la case ciblée pour connaitre les coordonnées
            String data = (String) target.getUserData();
            String[] arr = null;
            arr = data.split("-");
            List<String> list = Arrays.asList(arr);
            int x = Integer.parseInt(list.get(0));
            int y= Integer.parseInt(list.get(1));

            //On verifie si la loupe à été activée
            if(isLoupeActive)loupeCase(x, y);

            //On verifie si l'engrais à été activée
            if(isManureActive){
                model.setManure(x, y);
                isManureActive = false;
                isManureUsed = true;
                manure.setScaleX(1);
                manure.setScaleY(1);
                manure.setOpacity(0.5);
                grid.setCursor(Cursor.DEFAULT);
            }

            //On récupère la valeur de la case pour savoir s'il y a un champignons
            int resultCase = model.revealCase(x, y, true);

            updateCase(x, y, resultCase);


            if(model.getRestant() <= 0 || model.getChampiRestant() <= 0) {
                revealGrid();
                ShowPopUpEnd(model.finalScore());
            }
        }
        else{
            revealGrid();
            ShowPopUpEnd(model.finalScore());
        }
    }

    public void updateCase(int x, int y, int resultCase){
        //Par défaut on met l'image des feuilles (en cas de valeur renvoyée incorrecte on affiche tout de même les feuilles
        ImageView resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/feuille.png")));
        //Par défaut le texte affiché est nul
        Text txtNode = new Text("");

        //Si un champignon est trouvé on l'affiche
        if (resultCase == 1) {
            resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/champi.png")));
        }
        else if (resultCase == 4) {
            resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/badChampi.png")));
        }
        else if (resultCase == 5) {
            resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/megaChampi.png")));
        }
        //Sinon on récupère le nombre de champignons autour et on stocke la résultat dans le textenode
        else if(resultCase == 0) {
            int nbBombsAround = model.getNbBombsAround(x, y);
            txtNode.setText(String.valueOf(nbBombsAround));
            txtNode.setStyle("-fx-font: 30 arial;");
            txtNode.setFill(Color.WHITE);
            txtNode.setWrappingWidth(40);
            txtNode.setTextAlignment(TextAlignment.CENTER);
        }


        grid.add(resultImage, x, y);
        grid.add(txtNode, x, y);
    }


    public void loupeCase(int x, int y){
        System.out.println("Loupe used");
        if(!isLoupeUsed) {
            int maxYSize = model.grille.size() - 1;
            int maxXSize = model.grille.get(y).size() - 1;


            if (y > 0 && y < maxYSize && x > 0 && x < maxXSize) {
                updateCase(x, y-1, model.revealCase(x, y-1, false));
                updateCase(x-1, y-1, model.revealCase(x-1, y-1, false));
                updateCase(x+1, y-1, model.revealCase(x+1, y-1, false));
                updateCase(x-1, y, model.revealCase(x-1, y, false));
                updateCase(x+1, y, model.revealCase(x+1, y, false));
                updateCase(x, y+1, model.revealCase(x, y+1, false));
                updateCase(x-1, y+1, model.revealCase(x-1, y+1, false));
                updateCase(x+1, y+1, model.revealCase(x+1, y+1, false));

            }
            isLoupeActive = false;
            isLoupeUsed = true;
            loupe.setScaleX(1);
            loupe.setScaleY(1);
            loupe.setOpacity(0.5);
            grid.setCursor(Cursor.DEFAULT);
        }
    }


    public void placeBarriere(MouseEvent e){
        ImageView target = (ImageView) e.getTarget();
        System.out.println(target.getUserData());

        String data = (String) target.getUserData();
        String[] arr = null;
        arr = data.split("-");
        List<String> list = Arrays.asList(arr);

        ImageView resultImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/barriere.png")));
        resultImage.setUserData(data);
        resultImage.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY){
                clickedCase(event);

            }
            if(event.getButton() == MouseButton.SECONDARY){
                placeBarriere(event);
            }
        });
        grid.add(resultImage, Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
    }





    public void revealGrid(){
        grid.getChildren().clear();

        ArrayList<ArrayList> grille = model.getGrille();


        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;

                int resultCase = model.getCaseValue(x, y);


                updateCase(x, y, resultCase);
            }
        }


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
        if(!isLoupeUsed) {
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
        if(!isManureUsed) {
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
            System.out.println("Loupe active");
        }
    }

}
