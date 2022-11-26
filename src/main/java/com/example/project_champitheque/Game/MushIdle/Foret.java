package com.example.project_champitheque.Game.MushIdle;

public class Foret extends Usine{
    public Foret(){
        champi.setValue(0);
        type=TypeUsine.FORET;
        cost.setValue(1000);
        upagradeCost.setValue(100);
        image="foret.png";
        gain.setValue(10);
        level.setValue(1);
        lvlMax=10;
    }

    public Usine startItem() {
        Usine thread = new Foret();
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
