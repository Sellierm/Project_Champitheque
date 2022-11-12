package com.example.project_champitheque;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class MenuModel {
    private final IntegerProperty level;
    private final StringProperty pseudo;

    /*public MenuModel(Integer ranking, String name) {
        this.level = new SimpleIntegerProperty(ranking);
        this.pseudo = new SimpleStringProperty(name);
    }*/

    public MenuModel() {
        //Get all players
        List<String> allData = new ArrayList<>();
        try {
            File myObj = new File("C:/Users/bebew/OneDrive - JUNIA Grande école d'ingénieurs/CIR3/Semestre 1/Java/Projet/Project_Champitheque/src/main/resources/data/players.txt");
            //File myObj = new File(getClass().getResource("data/players.txt"));
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                allData.add(myReader.nextLine());
            }
            System.out.println(allData);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find players list.");
            e.printStackTrace();
        }

        //Get actual player
        int id = 0;
        try {
            File myObj = new File("C:/Users/bebew/OneDrive - JUNIA Grande école d'ingénieurs/CIR3/Semestre 1/Java/Projet/Project_Champitheque/src/main/resources/data/param.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                id = (Integer.parseInt(myReader.nextLine()) - 1)*3;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find actual player id.");
            e.printStackTrace();
        }

        this.level = new SimpleIntegerProperty(Integer.parseInt(allData.get(id+2)));
        this.pseudo = new SimpleStringProperty(allData.get(id));
    }


    public int getLevel() {
        return level.get();
    }
    public IntegerProperty levelProperty() {
        return level;
    }
    public String getPseudo() {
        return pseudo.get();
    }

    public StringProperty pseudoProperty() {
        return pseudo;
    }

}
