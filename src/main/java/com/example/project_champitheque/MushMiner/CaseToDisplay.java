package com.example.project_champitheque.MushMiner;

public class CaseToDisplay {

    public ValueCase value;

    public int champiAutour = 0;

    public boolean locked;

    public CaseToDisplay(ValueCase valeurCase, boolean locked, int nbChampiAutour){
        this.value = valeurCase;
        this.locked = locked;
        this.champiAutour = nbChampiAutour;
    }
}
