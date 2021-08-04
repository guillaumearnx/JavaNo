package fr.arnoux23u.javano.game;

import fr.arnoux23u.javano.cards.Carte;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Joueur implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    private final String nom;

    private final ArrayList<Carte> paquet;

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
}
