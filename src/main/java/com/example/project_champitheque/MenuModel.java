package com.example.project_champitheque;

import com.example.project_champitheque.FileManager.Read;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.*;

public class MenuModel {
    private final IntegerProperty score;
    private final StringProperty pseudo;
    private final IntegerProperty lvl;


    public MenuModel() {
        //Get all players
        Read reader = new Read();
        List<List<String>> allData = reader.readAllFromFile("players");


        //Get actual player
        int id = reader.getActualId();

        this.score = new SimpleIntegerProperty(Integer.parseInt(allData.get(id).get(1)));
        this.lvl = new SimpleIntegerProperty(Integer.parseInt(allData.get(id).get(2)));
        this.pseudo = new SimpleStringProperty(allData.get(id).get(0));
    }


    public int getScore() {
        return score.get();
    }
    public IntegerProperty scoreProperty() {
        return score;
    }

    public String getPseudo() {
        return pseudo.get();
    }
    public StringProperty pseudoProperty() {
        return pseudo;
    }

    public int getLvl() {
        return lvl.get();
    }
    public IntegerProperty lvlProperty() {
        return lvl;
    }

}
