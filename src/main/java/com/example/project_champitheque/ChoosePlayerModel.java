package com.example.project_champitheque;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ChoosePlayerModel {

    public List<String> allData = new ArrayList<>();

    // CONSTRUCTEUR
    public ChoosePlayerModel() {
        try {
            File myObj = new File("C:/Users/bebew/OneDrive - JUNIA Grande école d'ingénieurs/CIR3/Semestre 1/Java/Projet/Project_Champitheque/src/main/resources/data/players.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                allData.add(myReader.nextLine());
            }
            System.out.println(allData);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setParamPlayer(int playerId) {
        try {
            FileWriter myWriter = new FileWriter("C:/Users/bebew/OneDrive - JUNIA Grande école d'ingénieurs/CIR3/Semestre 1/Java/Projet/Project_Champitheque/src/main/resources/data/param.txt");
            myWriter.write(String.valueOf(playerId));
            myWriter.close();
            System.out.println("Successfully wrote player id to the param file.");
        } catch (IOException e) {
            System.out.println("An error occurred to write player id in param file.");
            e.printStackTrace();
        }
    }

    // GETTERS

    public List<String> getAllData() {
        return allData;
    }

    /*public String getNewName() {
        return newName.get();
    }

    public StringProperty newNameProperty() {
        return newName;
    }*/
}
