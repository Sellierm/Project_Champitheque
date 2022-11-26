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

public class GameTestController extends GameController {

    GameTestModel model;

    List<Usine> list;

    @FXML
    private GridPane grid;

    @FXML
    private Text money;

    @FXML
    private ImageView foret;
    @FXML
    private ImageView cave;
    @FXML
    private ImageView garage;


    private List<ImageView> listBuildButton;

    public void NewGame() {
        model.resetGame();
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
        return "GameTestScores";
    }
    public void setDifficulty(MouseEvent event){

    }


    public void initialize() {
        model = new GameTestModel();

        money.textProperty().bind(model.moneyProperty().asString());

        list = model.getList();

        upDateGrid();

        listBuildButton = new ArrayList<>();

        foret.setUserData(TypeUsine.FORET);
        listBuildButton.add(foret);

        cave.setUserData(TypeUsine.CAVE);
        listBuildButton.add(cave);

        garage.setUserData(TypeUsine.GARAGE);
        listBuildButton.add(garage);
    }

    public void upDateGrid() {
        System.out.println("Affichage");
        grid.getChildren().clear();

        int x = 0, y = 0;
        for (Usine usine : list) {
            HBox container = new HBox();

            VBox containerLeft = new VBox();

            VBox containerRight = new VBox();
            containerRight.alignmentProperty().set(Pos.CENTER);

            //Text de nombre de champis de l'usine

            Text txtNode = new Text();
            txtNode.textProperty().bind(usine.champiProperty().asString());
            txtNode.setStyle("-fx-font: 18 arial;");
            txtNode.setFill(Color.WHITE);
            txtNode.setWrappingWidth(80);
            txtNode.setTextAlignment(TextAlignment.CENTER);
            containerLeft.getChildren().add(txtNode);

            //Image de l'usine
            ImageView selectedImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/"+usine.getImage())));
            selectedImage.setOnMouseReleased(e -> {
                if(e.getButton() == MouseButton.PRIMARY){
                    click(e);
                }
            });
            selectedImage.setUserData(x);
            containerLeft.getChildren().add(selectedImage);

            //Infos sur l'usine
            HBox containerInfos = new HBox();
            //Gain
            Text txtNode2 = new Text();
            txtNode2.textProperty().bind(usine.gainProperty().asString());
            txtNode2.setStyle("-fx-font: 16 arial;");
            txtNode2.setFill(Color.WHITE);
            txtNode2.setWrappingWidth(40);
            txtNode2.setTextAlignment(TextAlignment.CENTER);
            containerInfos.getChildren().add(txtNode2);
            //Niveau
            Text txtNode3 = new Text();
            txtNode3.textProperty().bind(usine.levelProperty().asString());
            txtNode3.setStyle("-fx-font: 16 arial;");
            txtNode3.setFill(Color.WHITE);
            txtNode3.setWrappingWidth(40);
            txtNode3.setTextAlignment(TextAlignment.CENTER);
            containerInfos.getChildren().add(txtNode3);

            containerLeft.getChildren().add(containerInfos);


            //Actions sur l'usine
            //Upgrade
            Button upgrade = new Button();
            upgrade.textProperty().bind(usine.upagradeCostProperty().asString());
            upgrade.setStyle("-fx-background-color : #00ff00; -fx-background-radius : 30; -fx-font: 14 arial; -fx-text-fill: #ffffff;");
            upgrade.setUserData(x);
            upgrade.setOnAction(e -> {
                upgrade(e);
            });
            containerRight.getChildren().add(upgrade);
            //Sell
            Button sell = new Button();
            sell.setText("Vendre");
            sell.setStyle("-fx-background-color : #ff0000; -fx-background-radius : 30; -fx-font: 14 arial; -fx-text-fill: #ffffff;");
            sell.setUserData(x);
            sell.setOnAction(e -> {
                sell(e);
            });
            containerRight.getChildren().add(sell);

            container.getChildren().add(containerLeft);
            container.getChildren().add(containerRight);

            //On ajoute le tout
            grid.add(container, x%4, y);

            x++;
            if(x%4==0){
                y++;
            }
        }
    }


    public void buildUsine(MouseEvent event){
        System.out.println(event);
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


    public void cheat(KeyEvent event){
        if(event.getCode() == KeyCode.W) {
            model.cheat();
        }
        if(event.getCode() == KeyCode.A) {
            model.collectAll();
        }
    }

}
