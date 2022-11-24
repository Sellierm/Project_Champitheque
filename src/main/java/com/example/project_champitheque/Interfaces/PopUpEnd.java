package com.example.project_champitheque.Interfaces;

import com.example.project_champitheque.PowerMush.Joueur;

public interface PopUpEnd {
    public void ShowPopUpEnd(int score);
    public void ShowPopUpEnd(Joueur winner, int score);
    public void ClosePopUpEnd();
}
