package com.example.project_champitheque;


import com.example.project_champitheque.fileManager.Write;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ChoosePlayerModel {

    public List<List<String>> allData = new ArrayList<>();

    // CONSTRUCTEUR
    public ChoosePlayerModel() {
        try {
            File myObj = new File("src/main/resources/data/players.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] arr = null;
                arr = data.split(",");
                List<String> list = Arrays.asList(arr);
                allData.add(list);
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
            FileWriter myWriter = new FileWriter("src/main/resources/data/param.txt");
            myWriter.write(String.valueOf(playerId));
            myWriter.close();
            System.out.println("Successfully wrote player id to the param file.");
        } catch (IOException e) {
            System.out.println("An error occurred to write player id in param file.");
            e.printStackTrace();
        }
    }

    public void setNewPlayer(String newName) {
        String allLines = "";
        int compt = 0;
        try {
            File myObj = new File("src/main/resources/data/players.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                allLines+=myReader.nextLine()+'\n';
                compt++;
            }
            System.out.println(allLines);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find players list.");
            e.printStackTrace();
        }

        allLines+=newName+",0,0";


        try {
            FileWriter myWriter = new FileWriter("src/main/resources/data/players.txt");
            myWriter.write(allLines);
            myWriter.close();
            System.out.println("Successfully wrote new player to players file.");
        } catch (IOException e) {
            System.out.println("An error occurred to write new player to players file.");
            e.printStackTrace();
        }

        setParamPlayer(compt);


        //Ajout du joueur dans le global ranking
        /*Write writer = new Write();
        writer.writeNewLineInFile("0,0", "globalRanking");*/
    }

    // GETTERS

    public List<List<String>> getAllData() {
        return allData;
    }

    /*public String getNewName() {
        return newName.get();
    }

    public StringProperty newNameProperty() {
        return newName;
    }*/
}
