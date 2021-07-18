package game;

import game.utils.Carte;
import network.Client;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class Partie {
    private ArrayList<Carte> cartes;
    private final ArrayList<Joueur> joueurs;

    public Partie() {

        cartes = composer();
        Collections.shuffle(cartes);
        distribuer();
    }

    private void distribuer(){
        int indice = 0;
        for(int i = 0; i < 7; i++){
            for(Joueur j : joueurs){
                j.piocher(cartes.remove(indice));
                indice++;
            }
        }
    }

    public static ArrayList<Carte> composer() {
        Color[] couleurs = {Color.red, Color.blue, Color.green, Color.yellow};
        return new ArrayList<Carte>() {{
            for(int colind = 0; colind < 4; colind++){
                add(new Carte(couleurs[colind], 0));
                add(new Carte(couleurs[colind], 0));
                add(new Carte(couleurs[colind], 0));
                for(int i = 1; i < 10; i++){
                    add(new Carte(couleurs[colind], i));
                }
                for(int i = 0; i < 2; i++){
                    //+2 = 92
                    add(new Carte(couleurs[colind], 92));
                }
                for(int i = 0; i < 2; i++){
                    //Changement de sens = 96
                    add(new Carte(couleurs[colind], 96));
                }
                for(int i = 0; i < 2; i++){
                    //Skip = 40
                    add(new Carte(couleurs[colind], 40));
                }

            }
            for(int i = 0; i < 4; i++){
                //+4= 94
                add(new Carte(Color.black, 94));
            }
            for(int i = 0; i < 4; i++){
                //Changement couleur = 33
                add(new Carte(Color.black, 33));
            }
        }};
    }
}
