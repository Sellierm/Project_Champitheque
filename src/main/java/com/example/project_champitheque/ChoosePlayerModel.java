package com.example.project_champitheque;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChoosePlayerModel {

    // VARIABLE
    private final StringProperty newName;

    // CONSTRUCTEUR
    public ChoosePlayerModel(String newName) {
        this.newName = new SimpleStringProperty(newName);
    }

    // GETTERS

    public String getNewName() {
        return newName.get();
    }

    public StringProperty newNameProperty() {
        return newName;
    }
}
