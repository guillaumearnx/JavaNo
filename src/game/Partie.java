package game;

import game.utils.Carte;
import network.Client;
import network.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.*;

public class Partie extends Observable implements Serializable {

    private static final long serialVersionUID = 13L;

    private ArrayList<Carte> cartes;
    private final ArrayList<Joueur> joueurs;
    private Server s;

    public Partie(Server s) throws IOException {
        this.s = s;
        System.out.println("Partie cree");
        joueurs = new ArrayList<Joueur>();
        cartes = composer();
        Collections.shuffle(cartes);
    }

    public void distribuer() {
        int indice = 0;
        for (int i = 0; i < 7; i++) {
            for (Joueur j : joueurs) {
                Carte c = cartes.remove(indice);
                System.out.println("Joueur pioche une carte de couleur : " + c.color);
                j.piocher(cartes.remove(indice));
                indice++;
            }
        }
        s.sendToAllClients(this);
    }

    public static ArrayList<Carte> composer() throws IOException {
        Color[] couleurs = {Color.red, Color.blue, Color.green, Color.yellow};
        return new ArrayList<Carte>() {{
            for (int colind = 0; colind < 4; colind++) {
                add(new Carte(couleurs[colind], 0, ImageIO.read(new File(Carte.assetsDirectory + couleurs[colind].toString().toLowerCase() + File.separator, "0.png"))));
                for (int i = 1; i < 10; i++) {
                    add(new Carte(couleurs[colind], i, ImageIO.read(new File(Carte.assetsDirectory + couleurs[colind].toString().toLowerCase() + File.separator, i + ".png"))));
                }
                for (int i = 0; i < 2; i++) {
                    //+2 = 92
                    add(new Carte(couleurs[colind], 92, ImageIO.read(new File(Carte.assetsDirectory + couleurs[colind].toString().toLowerCase() + File.separator, "92.png"))));
                }
                for (int i = 0; i < 2; i++) {
                    //Changement de sens = 96
                    add(new Carte(couleurs[colind], 96, ImageIO.read(new File(Carte.assetsDirectory + couleurs[colind].toString().toLowerCase() + File.separator, "96.png"))));
                }
                for (int i = 0; i < 2; i++) {
                    //Skip = 40
                    add(new Carte(couleurs[colind], 40, ImageIO.read(new File(Carte.assetsDirectory + couleurs[colind].toString().toLowerCase() + File.separator, "40.png"))));
                }

            }
            final String parent = Carte.assetsDirectory + Color.black.toString().toLowerCase() + File.separator;
            for (int i = 0; i < 4; i++) {
                //+4= 94
                add(new Carte(Color.black, 94, ImageIO.read(new File(parent, "94.png"))));
            }
            for (int i = 0; i < 4; i++) {
                //Changement couleur = 33
                add(new Carte(Color.black, 33, ImageIO.read(new File(parent, "33.png"))));
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
        System.out.println("il y a " + joueurs.size() + " joeuur sdans la liste");
        System.out.println("a trouver : " + name);
        afficherJoueurs();
        for (Joueur j : joueurs) {
            System.out.println("Nom : " + j.getNom());
            if (j.getNom().equals(name)) {
                result = j;

                System.out.println("trouvé joueur avec nom : " + j.getNom());
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


    public void supprimerJoueur(String name) {
        joueurs.removeIf(player -> (player.getNom().equals(name)));
    }

    public void ajouterJoueur2(Joueur joueur) {
        joueurs.add(joueur);
    }
}
