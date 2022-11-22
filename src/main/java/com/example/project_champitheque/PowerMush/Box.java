package com.example.project_champitheque.PowerMush;

import java.util.Objects;

public class Box {

    private int value;


    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public Box(int value){
        this.value = value;
    }

    protected void newValue(int value){
        this.value = value;
    }

    public int getValue(){return this.value;}


}
