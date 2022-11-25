package com.example.project_champitheque.Game.ChampiMerge;

import com.example.project_champitheque.Application;
import com.example.project_champitheque.Game.GameController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;

public class ChampiMergeController extends GameController {

    ChampiMergeModel model;



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



    public void NewGame() {
        model.resetGame(this.size);
        setGrilleFX();
        ClosePopUpEnd();
    }


    public void ShowPopUpEnd(int score){
        String scoreTxt = ""+score;
        scoreNode1.setText(scoreTxt);
        popupend.setVisible(true);

    }

    public String getFileToReadStats(){
        return "ChampiMergeScores";
    }
    public void setDifficulty(MouseEvent event){

    }




    public void initialize(){
        this.size = 3;

        model = new ChampiMergeModel(this.size);

        scoreMerge.textProperty().bind(model.scoreMergeProperty().asString());

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
                    txtNode.setStyle("-fx-font: 18 arial;");
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
