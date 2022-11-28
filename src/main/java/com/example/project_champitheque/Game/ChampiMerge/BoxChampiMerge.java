package com.example.project_champitheque.Game.ChampiMerge;

public class BoxChampiMerge {

    private long value;


    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public BoxChampiMerge(long value){
        this.value = value;
    }

    protected void newValue(long value){
        this.value = value;
    }

    public long getValue(){return this.value;}

    public boolean divide(){
        if(value > 2){
            value/=2;
            return true;
        }
        return false;
    }

}
