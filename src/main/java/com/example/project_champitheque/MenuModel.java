package com.example.project_champitheque;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MenuModel {
    private final IntegerProperty level;
    private final StringProperty pseudo;

    public MenuModel(Integer ranking, String name) {
        this.level = new SimpleIntegerProperty(ranking);
        this.pseudo = new SimpleStringProperty(name);
    }

    public int getLevel() {
        return level.get();
    }
    public IntegerProperty levelProperty() {
        return level;
    }
    public String getPseudo() {
        return pseudo.get();
    }

    public StringProperty pseudoProperty() {
        return pseudo;
    }

}
