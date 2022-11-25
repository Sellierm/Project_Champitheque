package com.example.project_champitheque.Game;

import com.example.project_champitheque.Interfaces.EndGame;
import com.example.project_champitheque.FileManager.Write;

public abstract class GameModel implements EndGame {
    protected boolean gameEnd;

    protected int score;


    public void setGameEnd(){
        this.gameEnd = true;
        this.score = calculScore();
        Write writer = new Write();
        writer.writeScore(score, getFileToWriteStats());
    }
    public abstract String getFileToWriteStats();
    public void resetGameEnd(){
        this.gameEnd = false;
        this.score = 0;
    }
    public abstract int calculScore();
    public int getScore(){return this.score;}

    public boolean isGameEnd(){
        return gameEnd;
    }}
