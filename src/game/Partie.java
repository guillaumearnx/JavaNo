package game;

import datatransfer.SerialImage;
import game.utils.Carte;
import network.Server;

import javax.imageio.ImageIO;
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

    public Partie(Server s) throws IOException {
        this.s = s;
        System.out.println("Partie cree");
        joueurs = new ArrayList<Joueur>();
        posees = new ArrayList<Carte>();
        pioche = composer();
        Collections.shuffle(pioche);
        actualPlayer = joueurs.get(0);
    }

    public void distribuer() {
        int indice = 0;
        for (int i = 0; i < 7; i++) {
            for (Joueur j : joueurs) {
                Carte c = pioche.remove(indice);
                System.out.println("Joueur pioche une carte de couleur : " + c.color);
                j.piocher(pioche.remove(indice));
                indice++;
            }
        }
        posees.add(pioche.remove(0));
        s.sendToAllClients(this);
    }

    public ArrayList<Carte> getPioche() {
        return pioche;
    }

    public static ArrayList<Carte> composer() throws IOException {
        Color[] couleurs = {Color.red, Color.blue, Color.green, Color.yellow};
        return new ArrayList<Carte>() {{
            for (int colind = 0; colind < 4; colind++) {
                add(new Carte(couleurs[colind], 0, new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(couleurs[colind]) + File.separator, "0.png"))), false));
                for (int i = 1; i < 10; i++) {
                    add(new Carte(couleurs[colind], i, new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(couleurs[colind]) + File.separator, i + ".png"))), false));
                }
                for (int i = 0; i < 2; i++) {
                    //+2 = 92
                    add(new Carte(couleurs[colind], 92, new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(couleurs[colind]) + File.separator, "92.png"))), true));
                }
                for (int i = 0; i < 2; i++) {
                    //Changement de sens = 96
                    add(new Carte(couleurs[colind], 96, new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(couleurs[colind]) + File.separator, "96.png"))), true));
                }
                for (int i = 0; i < 2; i++) {
                    //Skip = 40
                    add(new Carte(couleurs[colind], 40, new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(couleurs[colind]) + File.separator, "40.png"))), true));
                }

            }
            final String parent = Carte.assetsDirectory + getNameFromColor(Color.black) + File.separator;
            for (int i = 0; i < 4; i++) {
                //+4= 94
                add(new Carte(Color.black, 94, new SerialImage(ImageIO.read(new File(parent, "94.png"))), true));
            }
            for (int i = 0; i < 4; i++) {
                //Changement couleur = 33
                add(new Carte(Color.black, 33, new SerialImage(ImageIO.read(new File(parent, "33.png"))), true));
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
        if(top.special){
            if(top.color == Color.black){
                return c.special && (c.color == Color.BLACK);
            }else{
                return c.color == top.color || c.value == top.value;
            }
        }else{
            if(c.special){
                if(c.color == Color.black){
                    return true;
                }else{
                    return c.color == top.color;
                }
            }else{
                return c.color == top.color || c.value == top.value;
            }
        }
    }

}
