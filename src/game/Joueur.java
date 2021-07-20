package game;

import game.utils.Carte;

import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Serializable {

    public final String nom;

    private ArrayList<Carte> paquet;

    public Joueur(String nom){
        this.nom = nom;
        paquet = new ArrayList<Carte>();
    }

    public void piocher(Carte pioche) {
        paquet.add(pioche);
    }

    public ArrayList<Carte> getCartes() {
        return paquet;
    }
}
