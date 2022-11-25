package com.example.project_champitheque;


import com.example.project_champitheque.FileManager.Read;
import com.example.project_champitheque.FileManager.Write;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class ChoosePlayerModel {

    public List<List<String>> allData = new ArrayList<>();

    // CONSTRUCTEUR
    public ChoosePlayerModel() {
        Read reader = new Read();
        allData = reader.readAllFromFile("players");
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

    public void setNewPlayer(String newName, String color) {

        Write writer = new Write();
        int index = writer.writeNewLineInFile(newName+",0,0,"+color, "players");
        setParamPlayer(index);

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
