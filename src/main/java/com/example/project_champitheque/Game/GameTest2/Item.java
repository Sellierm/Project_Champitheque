package com.example.project_champitheque.Game.GameTest2;

import java.util.List;

public class Item extends Thread {

    int life;
    String name;

    int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int y;

    int index;

    List<List<Integer>> path;

    public Item(String name, List<List<Integer>> path){
        life = 100;
        this.name = name;
        this.path = path;
        index = 0;
        x = this.path.get(index).get(0);
        y = this.path.get(index).get(1);
    }

    public void startItem() {
        Item thread = new Item(this.name, this.path);
        thread.start();
    }

    public void run() {
        while (index > path.size() && life > 0){
            System.out.println("Item : " + name + ", vie = "+life);
            life--;
            index++;
            x = this.path.get(index).get(0);
            y = this.path.get(index).get(1);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(life <= 0 || index <= path.size()){
            Thread.currentThread().interrupt();
            System.out.println("Dead");
        }
    }
}
