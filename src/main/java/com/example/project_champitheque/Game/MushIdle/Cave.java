package com.example.project_champitheque.Game.MushIdle;

public class Cave extends Usine {

    public Cave(){
        champi.setValue(0);
        type=TypeUsine.CAVE;
        cost.setValue(100000);
        upagradeCost.setValue(10000);
        mult = 10000;
        image="Cave.png";
        gain.setValue(800);
        level.setValue(1);
        lvlMax=10;
    }

    public Usine startItem() {
        Usine thread = new Cave();
        thread.start();
        return thread;
    }

    public void run() {
        while (this.alive){
            if(champi.get() < 100000000)champi.setValue(champi.get()+gain.get());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(!this.alive){
            Thread.currentThread().interrupt();
            System.out.println("Dead");
        }
    }
}
