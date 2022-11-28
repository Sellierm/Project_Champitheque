package com.example.project_champitheque.Game.MushIdle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Timer extends Thread {

    protected final IntegerProperty sec = new SimpleIntegerProperty();
    public IntegerProperty secProperty() {
        return sec;
    }

    protected final IntegerProperty min = new SimpleIntegerProperty();
    public IntegerProperty minProperty() {
        return min;
    }

    public Timer(){
        sec.setValue(0);
        min.setValue(5);
    }

    public Timer startTimer() {
        Timer thread = new Timer();
        thread.start();
        return thread;
    }

    public void run() {
        while (true){
            System.out.println("Run timer");
            if(sec.get() == 0){
                min.setValue(min.get()-1);
                sec.setValue(60);
            }
            sec.setValue(sec.get()-1);
            if(min.get() <= 0 && sec.get() <= 0){
                stopTimer();
                System.out.println("Stop timer");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void stopTimer(){
        Thread.currentThread().interrupt();
    }

    public void resetTimer(){
        min.setValue(5);
        sec.setValue(0);
    }
}
