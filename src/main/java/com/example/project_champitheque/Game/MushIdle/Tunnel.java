package com.example.project_champitheque.Game.MushIdle;

public class Tunnel extends Usine {

    public Tunnel(){
        champi.setValue(0);
        type=TypeUsine.CAVE;
        cost.setValue(1000000);
        upagradeCost.setValue(100000);
        mult = 100000;
        image="tunnel.png";
        gain.setValue(7000);
        level.setValue(1);
        lvlMax=10;
    }

    public Usine startItem() {
        Usine thread = new Tunnel();
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
