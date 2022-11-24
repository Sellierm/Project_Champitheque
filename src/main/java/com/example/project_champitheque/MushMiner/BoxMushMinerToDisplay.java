package com.example.project_champitheque.MushMiner;

public class BoxMushMinerToDisplay {

    public BoxValueMushMiner value;

    public int champiAutour = 0;

    public boolean locked;

    public BoxMushMinerToDisplay(BoxValueMushMiner valeurCase, boolean locked, int nbChampiAutour){
        this.value = valeurCase;
        this.locked = locked;
        this.champiAutour = nbChampiAutour;
    }
}
