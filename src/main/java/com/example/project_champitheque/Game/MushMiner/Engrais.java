package com.example.project_champitheque.Game.MushMiner;

import java.util.Random;

public class Engrais {

    private boolean used;
    protected boolean getUsed(){return used;}

    public Engrais(){
        this.used = false;

    }

    public boolean useEngrais(GrilleMushMiner plateau, int x, int y){
        if(!this.used){
            Random rand = new Random();

            //Random de nombres de champignons à ajouter
            int minNbChampi = 4;
            int maxNbChampi = 6;
            int randNbChampi = rand.nextInt(maxNbChampi - minNbChampi + 1) + minNbChampi;
            System.out.println(randNbChampi + " champignons ajoutés");

            int min = -2;
            int max = 2;
            //Boucle de randomise des positions de 2 cases autour de la case principale (différente de la case d'origine)
            for (int i = 0; i < randNbChampi; i++){
                int newX = x + rand.nextInt(max - min + 1) + min;
                int newY = y + rand.nextInt(max - min + 1) + min;
                if(!plateau.setEngraisCase(BoxValueMushMiner.CHAMPI, newX, newY)){
                    //Si la case est hors limite ou la case a été révélée
                    i--;
                }
            }

            int xVeneneux = x + rand.nextInt(max - min + 1) + min;
            int yVeneneux = y + rand.nextInt(max - min + 1) + min;
            while((xVeneneux == x && yVeneneux == y) || !plateau.setEngraisCase(BoxValueMushMiner.BAD, xVeneneux, yVeneneux)){
                System.out.println("Coordonnées invalides : " + xVeneneux + ", " + xVeneneux);
                xVeneneux = x + rand.nextInt(max - min + 1) + min;
                yVeneneux = y + rand.nextInt(max - min + 1) + min;
            }
            System.out.println("Champignon vénéneux en : " + xVeneneux + ", " + yVeneneux);

            int xJackpot = x + rand.nextInt(max - min + 1) + min;
            int yJackPot = y + rand.nextInt(max - min + 1) + min;
            while((xJackpot == x && yJackPot == y) || (xJackpot == xVeneneux && yJackPot == yVeneneux) || !plateau.setEngraisCase(BoxValueMushMiner.JACKPOT, xJackpot, yJackPot)){
                System.out.println("Coordonnées invalides : " + xJackpot + ", " + yJackPot);
                xJackpot = x + rand.nextInt(max - min + 1) + min;
                yJackPot = y + rand.nextInt(max - min + 1) + min;
            }
            System.out.println("Champignon jackpot en : " + xJackpot + ", " + yJackPot);
            this.used = true;
            return true;
        }
        else {
            return false;
        }

    }
}
