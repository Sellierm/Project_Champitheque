package com.example.project_champitheque.FileManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Read {


    public Read(){

    }

    public List<List<String>> readAllFromFile(String file){
        List<List<String>> allData = new ArrayList<>();
        try {
            File myObj = new File("src/main/resources/data/"+file+".txt");
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
        return allData;
    }

    protected List<String> readOneLine(int index, String file){
        List<List<String>> allData = new ArrayList<>();
        try {
            File myObj = new File("src/main/resources/data/"+file+".txt");
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

        return allData.get(index);
    }


    public String getName(int id){
        List<List<String>> allData = new ArrayList<>();
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
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find players list.");
            e.printStackTrace();
        }

        System.out.println(allData.get(id).get(0));
        return allData.get(id).get(0);
    }


    public String getActualName(){
        List<List<String>> allData = new ArrayList<>();
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
            System.out.println("An error occurred to find players list.");
            e.printStackTrace();
        }

        //Get actual player
        int id = getActualId();

        return allData.get(id).get(0);
    }

    public int getActualId(){
        //Get actual player
        int id = 0;
        try {
            File myObj = new File("src/main/resources/data/param.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                id = Integer.parseInt(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred to find actual player id.");
            e.printStackTrace();
        }
        return id;
    }

}
