package com.example.project_champitheque.fileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Write {

    Read reader;


    public Write(){
        reader = new Read();
    }


    public void writeNewLineInFile(String newLine, String file){
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
    }

    public void writeScore(int score, String file){


        writeNewLineInFile(reader.getActualId()+","+score, file);

    }
}
