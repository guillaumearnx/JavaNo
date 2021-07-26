package fr.arnoux23u.javano.game;

import fr.arnoux23u.javano.game.utils.Carte;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Joueur implements Serializable {

    private static final long serialVersionUID = 42L;

    private String nom;

    private ArrayList<Carte> paquet;

    public Joueur(String nom) {
        this.nom = nom;
        paquet = new ArrayList<Carte>();
    }

    public void piocher(Carte pioche) {
        paquet.add(pioche);
    }

    public ArrayList<Carte> getCartes() {
        return paquet;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joueur joueur)) return false;
        return Objects.equals(nom, joueur.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nom);
    }
}
