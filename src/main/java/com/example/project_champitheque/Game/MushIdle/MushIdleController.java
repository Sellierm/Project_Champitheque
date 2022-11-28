package com.example.project_champitheque.Game.MushIdle;

import com.example.project_champitheque.Application;
import com.example.project_champitheque.Game.GameController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MushIdleController extends GameController {

    MushIdleModel model;

    List<Usine> list;

    @FXML
    private GridPane grid;

    @FXML
    private Text money;
    @FXML
    private Text sec;
    @FXML
    private Text min;

    @FXML
    private ImageView foret;
    @FXML
    private ImageView cave;
    @FXML
    private ImageView garage;
    @FXML
    private ImageView tunnel;


    private List<ImageView> listBuildButton;

    public void NewGame() {
        model.resetGame();

        sec.textProperty().bind(model.getTimerEnd().secProperty().asString());

        min.textProperty().bind(model.getTimerEnd().minProperty().asString());

        upDateGrid();
    }

    @Override
    public void Quit() throws IOException {
        model.quit();
        super.Quit();
    }

    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        scoreNode1.setText(scoreTxt);
        popupend.setVisible(true);

    }

    public String getFileToReadStats(){
        return "MushIdleScores";
    }
    public void setDifficulty(MouseEvent event){

    }


    public void initialize() {
        model = new MushIdleModel();

        money.textProperty().bind(model.moneyProperty().asString());

        sec.textProperty().bind(model.getTimerEnd().secProperty().asString());

        min.textProperty().bind(model.getTimerEnd().minProperty().asString());

        list = model.getList();

        upDateGrid();

        listBuildButton = new ArrayList<>();

        foret.setUserData(TypeUsine.FORET);
        listBuildButton.add(foret);

        cave.setUserData(TypeUsine.CAVE);
        listBuildButton.add(cave);

        garage.setUserData(TypeUsine.GARAGE);
        listBuildButton.add(garage);

        tunnel.setUserData(TypeUsine.TUNNEL);
        listBuildButton.add(tunnel);
    }

    public void upDateGrid() {
        System.out.println("Affichage");
        grid.getChildren().clear();

        int x = 0, y = 0;
        for (Usine usine : list) {
            VBox container = new VBox();
            container.setStyle("-fx-border-color: white; -fx-border-radius: 10; -fx-border-width: 3px;");
            container.setMaxWidth(220);
            container.setMinWidth(220);
            container.setPrefWidth(220);
            container.setMaxHeight(180);
            container.setMinHeight(180);
            container.setPrefHeight(180);

            HBox containerTop = new HBox();
            containerTop.alignmentProperty().set(Pos.CENTER);
            containerTop.setPrefHeight(30);

            //Text de nombre de champis de l'usine
            Text txtChampi = new Text();
            txtChampi.textProperty().bind(usine.champiProperty().asString());
            txtChampi.setStyle("-fx-font: 18 arial;");
            txtChampi.setFill(Color.WHITE);
            //txtChampi.setWrappingWidth(120);
            txtChampi.setTextAlignment(TextAlignment.CENTER);
            containerTop.getChildren().add(txtChampi);

            HBox containerMidle = new HBox();

            HBox containerMidleLeft = new HBox();

            //Image de l'usine
            ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/"+usine.getImage())));
            selectedImage.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    click(e);
                }
            });
            selectedImage.setUserData(x);
            containerMidleLeft.getChildren().add(selectedImage);


            VBox containerMidleRight = new VBox();
            containerMidleRight.setPrefHeight(120);
            containerMidleRight.setPrefWidth(100);

            VBox containerMidleRightTop = new VBox();
            containerMidleRightTop.alignmentProperty().set(Pos.CENTER);
            containerMidleRightTop.setPrefHeight(60);
            containerMidleRightTop.setPrefWidth(100);
            //Actions sur l'usine
            //Upgrade
            Button upgrade = new Button();
            upgrade.textProperty().bind(usine.upagradeCostProperty().asString());
            upgrade.setStyle("-fx-background-color : #00ff00; -fx-background-radius : 30; -fx-font: 14 arial; -fx-text-fill: #ffffff;");
            upgrade.setUserData(x);
            upgrade.setOnAction(e -> {
                upgrade(e);
            });
            containerMidleRightTop.getChildren().add(upgrade);

            VBox containerMidleRightBottom = new VBox();
            containerMidleRightBottom.alignmentProperty().set(Pos.CENTER);
            containerMidleRightBottom.setPrefHeight(60);
            containerMidleRightBottom.setPrefWidth(100);
            //Sell
            Button sell = new Button();
            sell.setText("Vendre");
            sell.setStyle("-fx-background-color : #ff0000; -fx-background-radius : 30; -fx-font: 14 arial; -fx-text-fill: #ffffff;");
            sell.setUserData(x);
            sell.setOnAction(e -> {
                sell(e);
            });
            containerMidleRightBottom.getChildren().add(sell);

            containerMidleRight.getChildren().add(containerMidleRightTop);
            containerMidleRight.getChildren().add(containerMidleRightBottom);

            containerMidle.getChildren().add(containerMidleLeft);
            containerMidle.getChildren().add(containerMidleRight);


            //Infos sur l'usine
            HBox containerBottom = new HBox();
            containerBottom.setPrefHeight(30);

            HBox containerBottomLeft = new HBox();
            containerBottomLeft.alignmentProperty().set(Pos.CENTER);
            containerBottomLeft.setPrefWidth(140);
            containerBottomLeft.setPrefHeight(30);
            //Gain
            Text txtNode2 = new Text();
            txtNode2.textProperty().bind(usine.gainProperty().asString());
            txtNode2.setStyle("-fx-font: 16 arial;");
            txtNode2.setFill(Color.WHITE);
            //txtNode2.setWrappingWidth(60);
            txtNode2.setTextAlignment(TextAlignment.CENTER);
            containerBottomLeft.getChildren().add(txtNode2);

            HBox containerBottomRight = new HBox();
            containerBottomRight.alignmentProperty().set(Pos.CENTER);
            containerBottomRight.setPrefWidth(140);
            containerBottomRight.setPrefHeight(30);
            //Niveau
            Text txtNode3 = new Text();
            txtNode3.textProperty().bind(usine.levelProperty().asString());
            txtNode3.setStyle("-fx-font: 16 arial;");
            txtNode3.setFill(Color.WHITE);
            //txtNode3.setWrappingWidth(60);
            txtNode3.setTextAlignment(TextAlignment.CENTER);
            containerBottomRight.getChildren().add(txtNode3);

            containerBottom.getChildren().add(containerBottomLeft);
            containerBottom.getChildren().add(containerBottomRight);



            container.getChildren().add(containerTop);
            container.getChildren().add(containerMidle);
            container.getChildren().add(containerBottom);

            //On ajoute le tout
            grid.add(container, x%3, y);

            x++;
            if(x%3==0){
                y++;
            }
        }
    }


    public void buildUsine(MouseEvent event){
        TypeUsine type = (TypeUsine) (((Node) event.getTarget()).getUserData());
        model.buildUsine(type);
        upDateGrid();
    }

    public void collectAll(){
        model.collectAll();
    }


    public void click(MouseEvent event){
        model.click((int) (((Node) event.getTarget()).getUserData()));
    }

    public void upgrade(ActionEvent event){
        model.upgrade((int) (((Node) event.getTarget()).getUserData()));
    }

    public void sell(ActionEvent event){
        model.sell((int) (((Node) event.getTarget()).getUserData()));
        upDateGrid();
    }

    public void goldChampi(){
        if(model.goldChampi()){
            System.out.println("test goldChampi");
            ShowPopUpEnd(model.getScore());
        }
    }


    public void cheat(KeyEvent event){
        if(event.getCode() == KeyCode.W) {
            model.cheat();
        }
        if(event.getCode() == KeyCode.A) {
            model.collectAll();
        }
        if(event.getCode() == KeyCode.M) {
            model.cheatMoney();
        }
    }

}
