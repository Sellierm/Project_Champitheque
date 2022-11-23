package com.example.project_champitheque.ChampiMerge;

import com.example.project_champitheque.Application;
import com.example.project_champitheque.Interfaces.*;
import com.example.project_champitheque.fileManager.Read;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import java.util.List;

public class ChampiMergeController implements Quit, Help, NewGame, PopUpEnd, StatsGame {

    ChampiMergeModel model;

    @FXML
    private Button quit;

    @FXML
    private Pane popupend;
    @FXML
    private Text scoreNode;


    @FXML
    private Pane ranking;
    @FXML
    private VBox containerRanking;


    @FXML
    private Pane popuphelp;



    @FXML
    private GridPane grid;


    @FXML
    private Text scoreMerge;


    @FXML
    private Button size1;
    @FXML
    private Button size2;
    @FXML
    private Button size3;
    @FXML
    private Button size4;
    @FXML
    private Button size5;
    @FXML
    private Button size6;

    public int size;
    List<Button> tabSizeGrid = new ArrayList<>();

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
        model.resetGame(this.size);
        setGrilleFX();
        ClosePopUpEnd();
    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        scoreNode.setText(scoreTxt);
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
        List<List<String>> listRanking = reader.readAllFromFile("ChampiMergeScores");
        System.out.println(listRanking);
        listRanking.sort((elem1, elem2) -> Integer.parseInt(elem2.get(1)) - Integer.parseInt(elem1.get(1)));
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
        this.size = 3;

        model = new ChampiMergeModel(this.size);

        scoreMerge.textProperty().bind(model.scoreProperty().asString());

        setGrilleFX();

        size1.setScaleX(1.3);
        size1.setScaleY(1.3);
        size1.setUserData("3");
        size2.setUserData("4");
        size3.setUserData("5");
        size4.setUserData("6");
        size5.setUserData("7");
        size6.setUserData("8");
        this.tabSizeGrid.add(size1);
        this.tabSizeGrid.add(size2);
        this.tabSizeGrid.add(size3);
        this.tabSizeGrid.add(size4);
        this.tabSizeGrid.add(size5);
        this.tabSizeGrid.add(size6);
    }


    public void setGrilleFX(){
        grid.getChildren().clear();
        List<List<Integer>> grille = model.getGrilleToDisplay();
        for(int i = 0; i< grille.size(); i++){
            for(int j = 0; j < grille.get(i).size(); j++) {
                int x = j%grille.get(i).size();
                int y = i;
                String txt = x+"-"+y;

                Integer caseValue = grille.get(y).get(x);

                if(caseValue > 2048) {
                    Text txtNode = new Text(String.valueOf(caseValue));
                    txtNode.setStyle("-fx-font: 28 arial;");
                    txtNode.setFill(Color.WHITE);
                    txtNode.setWrappingWidth(60);
                    txtNode.setTextAlignment(TextAlignment.CENTER);
                    grid.add(txtNode, x, y);
                }
                else {
                    ImageView paneImage = new ImageView(new Image(Application.class.getResourceAsStream("/img/"+caseValue+".png")));
                    paneImage.setUserData(txt);
                    grid.add(paneImage, x, y);
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


    public void play(Direction direction){
        if(!model.isGameEnd()) {
            model.play(direction);
            setGrilleFX();
        }
        else {
            ShowPopUpEnd(model.getScore());
        }
    }

    public void keyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.DOWN){
            play(Direction.DOWN);
        }
        if(event.getCode() == KeyCode.UP){
            play(Direction.UP);
        }
        if(event.getCode() == KeyCode.LEFT){
            play(Direction.LEFT);
        }
        if(event.getCode() == KeyCode.RIGHT){
            play(Direction.RIGHT);
        }
    }


    public void changeSize(MouseEvent event){
        Node target = (Node) event.getTarget();
        String data = (String) target.getUserData();
        int valueData = Integer.parseInt(data);
        if(valueData >= 3 && valueData <= 8) {
            for (Button button : this.tabSizeGrid) {
                button.setScaleX(1);
                button.setScaleY(1);
            }
            target.setScaleX(1.3);
            target.setScaleY(1.3);
            size = valueData;
        }
    }


    public void goUp(){
        model.play(Direction.UP);
    }
    public void goDown(){
        model.play(Direction.DOWN);
    }
    public void goLeft(){
        model.play(Direction.LEFT);
    }
    public void goRight(){
        model.play(Direction.RIGHT);
    }
}
