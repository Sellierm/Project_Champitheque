package com.example.project_champitheque.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Write {

    Read reader;


    public Write(){
        reader = new Read();
    }


    public int writeNewLineInFile(String newLine, String file){
        String allLines = "";
        int compt = 0;
        try {
            File myObj = new File("src/main/resources/data/"+file+".txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                allLines+=myReader.nextLine()+'\n';
                compt++;
            }
            System.out.println(allLines);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find "+file);
            e.printStackTrace();
        }

        allLines+=newLine;


        try {
            FileWriter myWriter = new FileWriter("src/main/resources/data/"+file+".txt");
            myWriter.write(allLines);
            myWriter.close();
            System.out.println("Successfully wrote new player to "+file+" file.");
        } catch (IOException e) {
            System.out.println("An error occurred to write "+newLine+" to "+file+" file.");
            e.printStackTrace();
        }

        //On retourne la l'index de la ligne inserée
        return compt;
    }

    public void reWriteLine(int index, List<String> newLine, String file){
        List<List<String>> allData = reader.readAllFromFile(file);
        allData.remove(index);
        allData.add(index, newLine);

        String allLines = "";
        for(List<String> eachLine : allData){
            for (String eachvalue : eachLine){
                allLines+=eachvalue+",";
            }
            allLines+="\n";
        }

        try {
            FileWriter myWriter = new FileWriter("src/main/resources/data/"+file+".txt");
            myWriter.write(allLines);
            myWriter.close();
            System.out.println("Successfully wrote new player to "+file+" file.");
        } catch (IOException e) {
            System.out.println("An error occurred to write "+newLine+" to "+file+" file.");
            e.printStackTrace();
        }
    }

    public void writeScore(int score, String file){
        writeNewLineInFile(reader.getActualId()+","+score, file+"Scores");
        //writeGlobalRanking(score);



    }

    public void writeRanking(int score, int newScore, int newlvl, int oldlvl, int nbplays, String file){

        List<String> updatedRanking = new ArrayList<>();
        updatedRanking.add(String.valueOf(score));
        updatedRanking.add(String.valueOf(newlvl));
        updatedRanking.add(String.valueOf(nbplays));
        reWriteLine(reader.getActualId(), updatedRanking, file+"Ranking");

        writeGlobalRanking(newScore, newlvl - oldlvl);

    }

    public void writeGlobalRanking(int score, int ranking){
        List<String> players = reader.readOneLine(reader.getActualId(), "players");
        String name = players.get(0);
        int newScore = Integer.parseInt(players.get(1));
        newScore+=score;
        int newRanking = Integer.parseInt(players.get(2));
        newRanking+=ranking;
        String color = players.get(3);
        List<String> updatedRanking = new ArrayList<>();
        updatedRanking.add(name);
        updatedRanking.add(String.valueOf(newScore));
        updatedRanking.add(String.valueOf(newRanking));
        updatedRanking.add(color);
        reWriteLine(reader.getActualId(), updatedRanking, "players");

    }
}
