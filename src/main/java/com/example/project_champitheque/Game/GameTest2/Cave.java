package com.example.project_champitheque.Game.GameTest2;

public class Cave extends Usine {

    public Cave(){
        champi.setValue(0);
        type=TypeUsine.CAVE;
        cost.setValue(10000);
        image="Cave.png";
        gain.setValue(100);
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
            champi.setValue(champi.get()+gain.get());
            System.out.println(champi.get());
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
