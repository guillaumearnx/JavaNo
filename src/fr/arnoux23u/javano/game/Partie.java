package fr.arnoux23u.javano.game;

import fr.arnoux23u.javano.game.utils.Carte;
import fr.arnoux23u.javano.network.Server;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

public class Partie extends Observable implements Serializable {

    private static final long serialVersionUID = 13L;

    private ArrayList<Carte> pioche;
    private ArrayList<Carte> posees;
    private final ArrayList<Joueur> joueurs;
    private Joueur actualPlayer;
    private Server s;

    public Partie(Server s) throws IOException {
        this.s = s;
        System.out.println("Partie cree");
        joueurs = new ArrayList<Joueur>();
        posees = new ArrayList<Carte>();
        pioche = composer();
        Collections.shuffle(pioche);
    }

    public Partie(Server s, ArrayList<Joueur> players) throws IOException {
        this.s = s;
        System.out.println("Partie cree");
        joueurs = players;
        posees = new ArrayList<Carte>();
        pioche = composer();
        Collections.shuffle(pioche);
    }


    public void distribuer() {
        int indice = 0;
        for (int i = 0; i < 7; i++) {
            for (Joueur j : joueurs) {
                Carte c = pioche.get(indice);
                System.out.println("Joueur pioche une carte de couleur : " + c.color);
                j.piocher(pioche.remove(indice));
                indice++;
            }
        }
        posees.add(pioche.remove(0));
        if (s != null)
            s.sendToAllClients(this);
    }

    public ArrayList<Carte> getPioche() {
        return pioche;
    }

    public static ArrayList<Carte> composer() throws IOException {
        Color[] couleurs = {Color.red, Color.blue, Color.green, Color.yellow};
        return new ArrayList<Carte>() {{
            for (int colind = 0; colind < 4; colind++) {
                add(new Carte(couleurs[colind], 0));
                for (int i = 1; i < 10; i++) {
                    add(new Carte(couleurs[colind], i));
                    add(new Carte(couleurs[colind], i));
                }
                for (int i = 0; i < 2; i++) {
                    //+2 = 92
                    add(new Carte(couleurs[colind], 92));
                }
                for (int i = 0; i < 2; i++) {
                    //Changement de sens = 96
                    add(new Carte(couleurs[colind], 96));
                }
                for (int i = 0; i < 2; i++) {
                    //Skip = 40
                    add(new Carte(couleurs[colind], 40));
                }

            }
            final String parent = Carte.assetsDirectory + getNameFromColor(Color.black) + File.separator;
            for (int i = 0; i < 4; i++) {
                //+4= 94
                add(new Carte(Color.black, 94));
                add(new Carte(Color.black, 33));
            }
        }};
    }

    public int getConnectes() {
        return joueurs.size();
    }

    public void ajouterJoueur(String name) {
        joueurs.add(new Joueur(name));
        System.out.println("J'ajoute un joueur .... | nb -> " + joueurs.size());
        setChanged();
        notifyObservers();
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueurByName(String name) {
        Joueur result = null;
        for (Joueur j : joueurs) {
            if (j.getNom().equals(name)) {
                result = j;
                break;
            }
        }
        return result;
    }

    public void afficherJoueurs() {
        System.out.println("---affichage partie-----");
        System.out.println("Partie contient " + joueurs.size() + " joueurs");
        for (Joueur j : joueurs) {
            System.out.println("Joueur : Nom -> " + j.getNom());
            System.out.println("Joueur possede " + j.getCartes().size() + " cartes");
            int i = 0;
            for (Carte c : j.getCartes()) {
                System.out.println("Carte " + i + " de couleur " + c.color + " de valeur : " + c.value);
                i++;
            }
        }
    }


    public void supprimerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
        setChanged();
        notifyObservers();
    }

    public static String getNameFromColor(Color c) {
        return switch (c.toString().toLowerCase()) {
            case "java.awt.color[r=0,g=0,b=0]" -> "black";
            case "java.awt.color[r=0,g=0,b=255]" -> "blue";
            case "java.awt.color[r=255,g=255,b=0]" -> "yellow";
            case "java.awt.color[r=255,g=0,b=0]" -> "red";
            case "java.awt.color[r=0,g=255,b=0]" -> "green";
            default -> null;
        };
    }

    public ArrayList<Carte> getPosees() {
        return posees;
    }

    public boolean peutPoser(Joueur j, Carte c) {
        if (j != actualPlayer)
            return false;
        Carte top = posees.get(posees.size() - 1);
        return superposerCartes(c, top);
    }

    public static boolean superposerCartes(Carte dessus, Carte dessous) {
        switch (dessous.value) {
            //NORMAL
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                switch (dessus.value) {
                    //NORMAL
                    case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                        return dessous.value == dessus.value || dessous.color == dessus.color;
                    }
                    //+2
                    // SKIP
                    // REVERSE
                    case 92, 96, 40 -> {
                        return dessous.color == dessus.color;
                    }
                    //+4
                    // COLOR
                    case 94, 33 -> {
                        return true;
                    }
                }
            }
            //+2
            //+4
            case 92, 94 -> {
                if (dessous.value == dessus.value) {
                    return true;
                }
                if (dessous.isChoosed()) {
                    switch (dessus.value) {
                        //NORMAL
                        case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 40, 92, 96 -> {
                            return dessous.getChoosed() == dessus.color;
                        }
                        //+4
                        case 94, 33 -> {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
            //SKIP
            case 40, 96 -> {
                if (dessous.value == dessus.value) {
                    return true;
                }
                System.out.println("dessous is : " + dessous.value + "/" + getNameFromColor(dessous.color));
                System.out.println("dessus is : " + dessus.value + "/" + getNameFromColor(dessus.color));
                switch (dessus.value) {
                    //NORMAL
                    case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 40, 92, 96 -> {
                        return dessous.color == dessus.color;
                    }
                    //COLOR
                    case 33, 94 -> {
                        return true;
                    }
                }
            }
            case 33 -> {
                if (dessous.value == dessus.value) {
                    return true;
                }
                if (dessous.isChoosed()) {
                    switch (dessus.value) {
                        case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 92, 96, 40 -> {
                            return dessous.getChoosed() == dessus.color;
                        }
                        case 94 -> {
                            return true;
                        }
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
