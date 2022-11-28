package com.example.project_champitheque.Game.MushIdle;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;
import java.util.List;

public class GameTestModel {

    public List<Usine> getList() {
        return listUsine;
    }
    private List<Usine> listUsine = new ArrayList<>();

    private Timer timerEnd = new Timer();
    public Timer getTimerEnd(){
        return timerEnd;
    }

    private final LongProperty money = new SimpleLongProperty();
    public LongProperty moneyProperty() {
        return money;
    }


    private static int maxUsine = 16;


    public GameTestModel(){
        start();
    }

    public void resetGame(){
        start();
    }

    public void start(){

        quit();
        listUsine.clear();

        timerEnd = timerEnd.startTimer();
        timerEnd.resetTimer();

        money.setValue(1100);

        buildUsine(TypeUsine.FORET);
    }

    public void buildUsine(TypeUsine type){
        if(listUsine.size() < maxUsine) {
            switch (type){
                case FORET:
                    Usine newforet = new Foret();
                    if(money.get() >= newforet.getCost()) {
                        listUsine.add(newforet.startItem());
                        money.setValue(money.get()-newforet.getCost());
                    }
                    break;
                case CAVE:
                    Usine newCave = new Cave();
                    if(money.get() >= newCave.getCost()) {
                        listUsine.add(newCave.startItem());
                        money.setValue(money.get()-newCave.getCost());
                    }
                    break;
                case GARAGE:
                    Usine newGarage = new Garage();
                    if(money.get() >= newGarage.getCost()) {
                        listUsine.add(newGarage.startItem());
                        money.setValue(money.get()-newGarage.getCost());
                    }
                    break;
                case TUNNEL:
                    Usine newTunnel = new Tunnel();
                    if(money.get() >= newTunnel.getCost()) {
                        listUsine.add(newTunnel.startItem());
                        money.setValue(money.get()-newTunnel.getCost());
                    }
                    break;
            }
        }
    }

    public void collectAll(){
        long total = 0;
        for(Usine eachUsine : listUsine){
            total+=eachUsine.collect();
        }
        money.setValue(money.get()+total);
    }

    public void click(int index){
        if(index < maxUsine)listUsine.get(index).increaseManual();
    }

    public void upgrade(int index){
        if(index < maxUsine){
            Usine acutalUsine = listUsine.get(index);
            if(acutalUsine.getUpagradeCost() <= money.get()) {
                money.setValue(money.get() -listUsine.get(index).upgrade());
            }
        }
    }

    public void sell(int index){
        if(index < maxUsine){
            listUsine.get(index).sellUsine();
            listUsine.remove(index);
        }
    }


    public void cheat(){
        for(Usine eachUsine : listUsine){
            eachUsine.increaseManual();
        }
    }


    public void quit(){
        for(Usine eachUsine : listUsine){
            eachUsine.sellUsine();
            timerEnd.stopTimer();
        }
    }
}
