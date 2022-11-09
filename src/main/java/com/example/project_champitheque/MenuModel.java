package com.example.project_champitheque;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class MenuModel {
    private final IntegerProperty level;
    private final StringProperty pseudo;

    /*public MenuModel(Integer ranking, String name) {
        this.level = new SimpleIntegerProperty(ranking);
        this.pseudo = new SimpleStringProperty(name);
    }*/

    public MenuModel(Integer ranking, String name) {
        try {
            File myObj = new File("../../resources/data/players.txt");
            Scanner myReader = new Scanner(myObj);
            /*while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
            }*/
            System.out.println(myReader);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        this.level = new SimpleIntegerProperty(ranking);
        this.pseudo = new SimpleStringProperty(name);
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
