package game;

import game.utils.Carte;
import network.Client;
import network.Server;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.*;

public class Partie extends Observable implements Serializable {

    private ArrayList<Carte> cartes;
    private final ArrayList<Joueur> joueurs;

    public Partie() {
        System.out.println("Partie cree");
        joueurs = new ArrayList<Joueur>();
        cartes = composer();
        Collections.shuffle(cartes);
        distribuer();
    }

    private void distribuer() {
        int indice = 0;
        for (int i = 0; i < 7; i++) {
            for (Joueur j : joueurs) {
                j.piocher(cartes.remove(indice));
                indice++;
            }
        }
    }

    public static ArrayList<Carte> composer() {
        Color[] couleurs = {Color.red, Color.blue, Color.green, Color.yellow};
        return new ArrayList<Carte>() {{
            for (int colind = 0; colind < 4; colind++) {
                add(new Carte(couleurs[colind], 0));
                add(new Carte(couleurs[colind], 0));
                add(new Carte(couleurs[colind], 0));
                for (int i = 1; i < 10; i++) {
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
            for (int i = 0; i < 4; i++) {
                //+4= 94
                add(new Carte(Color.black, 94));
            }
            for (int i = 0; i < 4; i++) {
                //Changement couleur = 33
                add(new Carte(Color.black, 33));
            }
        }};
    }

    public int getConnectes() {
        return joueurs.size();
    }

    public void ajouterJoueur(String name) {
        joueurs.add(new Joueur(name));
        System.out.println("J'ajoute un joueur .... | nb -> "+joueurs.size());
        setChanged();
        notifyObservers();
    }

    public ArrayList<Joueur> getJoueurs() {
        return joueurs;
    }

    public Joueur getJoueurByName(String name){
        Joueur result = null;
        System.out.println("il y a "+joueurs.size()+" joeuur sdans la liste");
        System.out.println("a trouver : "+name);
        for(Joueur j :joueurs){
            System.out.println("Nom : "+j.nom);
            if(j.nom.equals(name)){
                result = j;

                System.out.println("trouvé joueur avec nom : "+j.nom);
                break;
            }
        }
        afficherJoueurs();
        return result;
    }

    public void afficherJoueurs(){
        for(Joueur j : joueurs){
            System.out.println(j);
            System.out.println(j.nom);
            System.out.println("--------");
        }
    }


    public void supprimerJoueur(String name) {
        joueurs.removeIf(player -> (player.nom.equals(name)));
        setChanged();
        notifyObservers();
    }
}
