package com.example.project_champitheque.Game.GameTest2;

import java.util.ArrayList;
import java.util.List;

public class GameTestModel {

    public List<Item> getList() {
        return list;
    }

    private List<Item> list;

    private static int sizeX = 10;
    private static int sizeY = 10;
    public static int getSizeX() {
        return sizeX;
    }
    public static int getSizeY() {
        return sizeY;
    }


    private List<List<Integer>> path;

    public GameTestModel(){
        start();
    }

    public void resetGame(){
        start();
    }

    public void start(){
        path = new ArrayList<>();
        List<Integer> tmpPath = new ArrayList<>();
        tmpPath.add(10);
        tmpPath.add(10);
        path.add(tmpPath);

        tmpPath = new ArrayList<>();
        tmpPath.add(9);
        tmpPath.add(9);
        path.add(tmpPath);

        tmpPath = new ArrayList<>();
        tmpPath.add(7);
        tmpPath.add(7);
        path.add(tmpPath);

        tmpPath = new ArrayList<>();
        tmpPath.add(6);
        tmpPath.add(6);
        path.add(tmpPath);

        tmpPath = new ArrayList<>();
        tmpPath.add(5);
        tmpPath.add(5);
        path.add(tmpPath);

        System.out.println(path);


        list = new ArrayList<>();
        for(int i =0; i < 1; i++){
            System.out.println("Création item");
            list.add(new Item("Thread-"+i, this.path));

        }
        for(Item itemList : list){
            itemList.start();
        }
    }
}
