package com.example.project_champitheque.ChampiMerge;

public class BoxChampiMerge {

    private int value;


    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public BoxChampiMerge(int value){
        this.value = value;
    }

    protected void newValue(int value){
        this.value = value;
    }

    public int getValue(){return this.value;}

}
