package main.java.game;

import main.java.game.cards.Carte;
import main.java.game.cards.Deck;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Joueur implements Serializable {

    @Serial
    private static final long serialVersionUID = 42L;

    private final String nom;

    private final Deck deck;

    public Joueur(String nom) {
        this.nom = nom;
         deck = new Deck();
    }

    public void piocher(Carte pioche) {
        deck.piocher(pioche);
    }

    public ArrayList<Carte> getCartes() {
        return deck.getCartes();
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

    public Deck getDeck() {
        return deck;
    }
}
