package fr.arnoux23u.javano.mvc.model;

import fr.arnoux23u.javano.cards.TypesCartes;
import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.game.Carte;
import fr.arnoux23u.javano.network.Server;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Partie extends Observable implements Serializable {

    private static final long serialVersionUID = 13L;

    private ArrayList<Carte> pioche;
    private ArrayList<Carte> posees;
    private final ArrayList<Joueur> joueurs;
    private Joueur actualPlayer;
    private Server s;
    private boolean enCours;

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
        do
            posees.add(pioche.remove(0));
        while (posees.get(posees.size() - 1).type != TypesCartes.NORMAL);

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

    public void lancer() {
        enCours = true;
        distribuer();
        setChanged();
        notifyObservers();
        /*while (enCours) {
            int indice = 0;
            actualPlayer = joueurs.get(indice);
            Joueur actualPlayerCopy = actualPlayer;
            /*Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (actualPlayer.equals(actualPlayerCopy)) {
                        getNextJoueur(indice);
                        System.out.println("PD");
                    }


                }
            }, 1000 * 5, 1000 * 5);
        ActionHandler ah = s.getNextAc
        }*/
    }

    private void getNextJoueur(int indice) {
        System.out.println("Actual player is : "+actualPlayer.getNom());
        int nbj = joueurs.size() - 1;
        indice++;
        System.out.println("add 1 to queue");
        if (indice > nbj) {
            indice = 0;
            System.out.println("end of queue, restart list at 0");
        }
        actualPlayer = joueurs.get(indice);
        System.out.println("new player is : "+actualPlayer.getNom());
    }

    public void arreter() {
        enCours = false;
        setChanged();
        notifyObservers();
    }

    public boolean isEnCours() {
        return enCours;
    }

    public Joueur getActualPlayer() {
        return actualPlayer;
    }
}
