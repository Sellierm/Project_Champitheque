package com.example.project_champitheque.Game;

import com.example.project_champitheque.FileManager.Read;
import com.example.project_champitheque.Interfaces.EndGame;
import com.example.project_champitheque.FileManager.Write;

import java.util.List;

public abstract class GameModel implements EndGame {
    protected boolean gameEnd;
    public boolean isGameEnd(){
        return gameEnd;
    }

    protected int score;
    public int getScore(){return this.score;}

    private static int facteurlvl = 200;

    public void setGameEnd(){
        this.gameEnd = true;
        this.score = calculScore();
        Write writer = new Write();
        writer.writeScore(score, getFileToWriteStats());

        Read reader = new Read();
        List<String> player = reader.readOneLine(reader.getActualId(), getFileToWriteStats()+"Ranking");
        int tmpScore = Integer.parseInt(player.get(0));
        tmpScore+=this.score;
        int newlvl = tmpScore/facteurlvl;
        int oldlvl = Integer.parseInt(player.get(1));
        int tmpNbPlays = Integer.parseInt(player.get(2));
        tmpNbPlays++;
        writer.writeRanking(tmpScore, this.score, newlvl, oldlvl, tmpNbPlays, getFileToWriteStats());
    }
    public void resetGameEnd(){
        this.gameEnd = false;
        this.score = 0;
    }
    public abstract int calculScore();
    public abstract String getFileToWriteStats();

}
