package fr.arnoux23u.javano.tests;

import fr.arnoux23u.javano.cards.Cartes;
import fr.arnoux23u.javano.game.Joueur;
import fr.arnoux23u.javano.game.Partie;
import fr.arnoux23u.javano.game.utils.Carte;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestPartie {

    private Partie p;

    @Test
    public void test00_creationPartie() throws IOException {
        p = new Partie(null);
        //Partie deja cree par before
        assertEquals("Devrait contenir 108 cartes", 108, p.getPioche().size());
        assertEquals("Posees devraient etre vides", 0, p.getPosees().size());
    }

    @Test
    public void test01_distribution() throws IOException {
        p = new Partie(null, new ArrayList<Joueur>() {{
            add(new Joueur("Player1234"));
            add(new Joueur("Player1233"));
            add(new Joueur("Player1235"));
        }});
        p.distribuer();
        ArrayList<Joueur> pl = p.getJoueurs();
        assertEquals("Joueur 1 devrait avoir 7 cartes dans paquet", 7, pl.get(0).getCartes().size());
        assertEquals("Joueur 2 devrait avoir 7 cartes dans paquet", 7, pl.get(1).getCartes().size());
        assertEquals("Joueur 3 devrait avoir 7 cartes dans paquet", 7, pl.get(2).getCartes().size());
        System.out.println(pl.size() + " joueurs dans liste");
        System.out.println("joueur possede " + pl.get(0).getCartes().size() + " cartes");
        assertEquals("Devrait contenir 108 cartes moins distrib = 86", 108 - (pl.size() * 7L) - 1, p.getPioche().size());
        assertEquals("Posees devrait contenir premiere carte", 1, p.getPosees().size());
    }

    @Test
    public void test02_peutPoser_carteNormale() throws IOException {
        Carte rouge4 = new Carte(Color.RED, 4);
        Carte jaune4 = new Carte(Color.YELLOW, 4);
        Carte rouge5 = new Carte(Color.RED, 5);
        Carte vert0 = new Carte(Color.GREEN, 0);
        Carte plus2rouge = new Carte(Color.RED, 92);
        Carte plus2jaune = new Carte(Color.YELLOW, 92);
        Carte plus4 = new Carte(Color.BLACK, 94);
        Carte skip = new Carte(Color.RED, 40);
        Carte reverse = new Carte(Color.YELLOW, 96);
        Carte color = new Carte(Color.BLACK, 33);

        assertTrue("Poser couleur", Partie.superposerCartes(jaune4, rouge4));
        assertTrue("Poser couleur", Partie.superposerCartes(rouge5, rouge4));
        assertTrue("Poser +2 couleur", Partie.superposerCartes(plus2rouge, rouge4));
        assertTrue("Poser +4", Partie.superposerCartes(plus4, rouge4));
        assertTrue("Poser Skip", Partie.superposerCartes(skip, rouge4));
        assertTrue("Poser changement couleur", Partie.superposerCartes(color, rouge4));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reverse, rouge4));
        assertFalse("Peut pas poser +2 couleur", Partie.superposerCartes(plus2jaune, rouge4));
        assertFalse("Peut pas poser", Partie.superposerCartes(vert0, rouge4));
    }

    @Test
    public void test03_peutPoser_plusDeux() throws IOException {
        Carte plus2jaune = new Carte(Color.YELLOW, 92);
        Carte plus2jaune2 = new Carte(Color.RED, 92);
        Carte plus2vert = new Carte(Color.GREEN, 92);
        Carte vert0 = new Carte(Color.GREEN, 0);
        Carte plus4 = new Carte(Color.BLACK, 94);
        Carte skipred = new Carte(Color.RED, 40);
        Carte skipyellow = new Carte(Color.YELLOW, 40);
        Carte reversered = new Carte(Color.RED, 96);
        Carte reversejaune = new Carte(Color.YELLOW, 96);
        Carte color = new Carte(Color.BLACK, 33);

        assertFalse("Peut pas poser normal", Partie.superposerCartes(vert0, plus2jaune));
        assertFalse("Peut pas poser plus4", Partie.superposerCartes(plus4, plus2jaune));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skipred, plus2jaune));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reversered, plus2jaune));
        assertFalse("Peut pas poser color", Partie.superposerCartes(color, plus2jaune));
        assertTrue("Peut poser +2 meme couleur", Partie.superposerCartes(plus2jaune2, plus2jaune));
        assertTrue("Peut poser +2 pas meme couleur", Partie.superposerCartes(plus2vert, plus2jaune));

        plus2jaune.setChoosed(plus2jaune2.color);
        Carte jaune6 = new Carte(Color.YELLOW, 6);

        assertFalse("Peut pas poser normal couleur", Partie.superposerCartes(vert0, plus2jaune));
        assertTrue("Peut poser normal couleur", Partie.superposerCartes(jaune6, plus2jaune));
        assertTrue("Peut poser plus4", Partie.superposerCartes(plus4, plus2jaune));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skipred, plus2jaune));
        assertTrue("Peut poser skip", Partie.superposerCartes(skipyellow, plus2jaune));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reversered, plus2jaune));
        assertTrue("Peut poser reverse", Partie.superposerCartes(reversejaune, plus2jaune));
        assertTrue("Peut poser color", Partie.superposerCartes(color, plus2jaune));
        assertTrue("Peut poser +2 meme couleur", Partie.superposerCartes(plus2jaune2, plus2jaune));
        assertTrue("Peut poser +2 pas meme couleur", Partie.superposerCartes(plus2vert, plus2jaune));
    }

    @Test
    public void test04_peutPoser_plusQuatre() throws IOException {
        Carte plusquatre = new Carte(Color.BLACK, 92);
        Carte plusquatre2 = new Carte(Color.BLACK, 92);
        Carte plus2vert = new Carte(Color.GREEN, 92);
        Carte vert0 = new Carte(Color.GREEN, 0);
        Carte bleu0 = new Carte(Color.BLUE, 0);
        Carte skipred = new Carte(Color.RED, 40);
        Carte skipgreen = new Carte(Color.GREEN, 40);
        Carte reversegreen = new Carte(Color.GREEN, 96);
        Carte reverseyellow = new Carte(Color.YELLOW, 96);
        Carte color = new Carte(Color.BLACK, 33);

        assertFalse("Peut pas poser normal", Partie.superposerCartes(vert0, plusquatre));
        assertTrue("Peut poser plus4", Partie.superposerCartes(plusquatre2, plusquatre));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skipgreen, plusquatre));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reversegreen, plusquatre));
        assertFalse("Peut pas poser color", Partie.superposerCartes(color, plusquatre));
        assertFalse("Peut pas poser +2", Partie.superposerCartes(plus2vert, plusquatre));

        plusquatre.setChoosed(Color.GREEN);

        assertFalse("Peut pas poser normal", Partie.superposerCartes(bleu0, plusquatre));
        assertTrue("Peut poser normal couleur", Partie.superposerCartes(vert0, plusquatre));
        assertTrue("Peut poser plus4", Partie.superposerCartes(plusquatre2, plusquatre));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skipred, plusquatre));
        assertTrue("Peut poser skip", Partie.superposerCartes(skipgreen, plusquatre));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reverseyellow, plusquatre));
        assertTrue("Peut poser reverse", Partie.superposerCartes(reversegreen, plusquatre));
        assertTrue("Peut pas poser color", Partie.superposerCartes(color, plusquatre));
    }


    @Test
    public void test05_peutPoser_reverse() throws IOException {
        Carte reverse = new Carte(Color.YELLOW, 96);
        Carte rouge4 = new Carte(Color.RED, 4);
        Carte jaune4 = new Carte(Color.YELLOW, 4);
        Carte plus2jaune = new Carte(Color.YELLOW, 92);
        Carte plus2vert = new Carte(Color.GREEN, 92);
        Carte plus4 = new Carte(Color.BLACK, 94);
        Carte reversejaune = new Carte(Color.YELLOW, 96);
        Carte reverserouge = new Carte(Color.RED, 96);
        Carte skipjaune = new Carte(Color.YELLOW, 40);
        Carte skiprouge = new Carte(Color.RED, 40);
        Carte color = new Carte(Color.BLACK, 33);

        assertTrue("Poser normal couleur", Partie.superposerCartes(jaune4, reverse));
        assertFalse("Peut pas poser normal", Partie.superposerCartes(rouge4, reverse));
        assertTrue("Poser +2 couleur", Partie.superposerCartes(plus2jaune, reverse));
        assertFalse("Peut pas poser +2", Partie.superposerCartes(plus2vert, reverse));
        assertTrue("Poser +4", Partie.superposerCartes(plus4, reverse));
        assertTrue("Poser reverse couleur", Partie.superposerCartes(reversejaune, reverse));
        assertTrue("Poser reverse", Partie.superposerCartes(reverserouge, reverse));
        assertTrue("Peut poser skip", Partie.superposerCartes(skipjaune, reverse));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skiprouge, reverse));
        assertTrue("Poser changement couleur", Partie.superposerCartes(color, reverse));

    }

    @Test
    public void test06_peutPoser_skip() throws IOException {
        Carte skip = new Carte(Color.YELLOW, 40);
        Carte rouge4 = new Carte(Color.RED, 4);
        Carte jaune4 = new Carte(Color.YELLOW, 4);
        Carte plus2jaune = new Carte(Color.YELLOW, 92);
        Carte plus2vert = new Carte(Color.GREEN, 92);
        Carte plus4 = new Carte(Color.BLACK, 94);
        Carte reversejaune = new Carte(Color.YELLOW, 96);
        Carte reverserouge = new Carte(Color.RED, 96);
        Carte skipjaune = new Carte(Color.YELLOW, 40);
        Carte skiprouge = new Carte(Color.RED, 40);
        Carte color = new Carte(Color.BLACK, 33);

        assertTrue("Poser normal couleur", Partie.superposerCartes(jaune4, skip));
        assertFalse("Peut pas poser normal", Partie.superposerCartes(rouge4, skip));
        assertTrue("Poser +2 couleur", Partie.superposerCartes(plus2jaune, skip));
        assertFalse("Peut pas poser +2", Partie.superposerCartes(plus2vert, skip));
        assertTrue("Poser +4", Partie.superposerCartes(plus4, skip));
        assertTrue("Poser reverse couleur", Partie.superposerCartes(reversejaune, skip));
        assertFalse("Poser reverse", Partie.superposerCartes(reverserouge, skip));
        assertTrue("Peut poser skip", Partie.superposerCartes(skipjaune, skip));
        assertTrue("Peut pas poser skip", Partie.superposerCartes(skiprouge, skip));
        assertTrue("Poser changement couleur", Partie.superposerCartes(color, skip));

    }

    @Test
    public void test07_peutPoser_color() throws IOException {
        Carte color = new Carte(Color.BLACK, 33);
        Carte rouge4 = new Carte(Color.RED, 4);
        Carte plus2jaune = new Carte(Color.YELLOW, 92);
        Carte plus2rouge = new Carte(Color.RED, 92);
        Carte plus4 = new Carte(Color.BLACK, 94);
        Carte reversejaune = new Carte(Color.YELLOW, 96);
        Carte reverserouge = new Carte(Color.RED, 96);
        Carte skipjaune = new Carte(Color.YELLOW, 40);
        Carte skiprouge = new Carte(Color.RED, 40);
        Carte color2 = new Carte(Color.BLACK, 33);

        assertFalse("Peut pas poser car aucune couleur", Partie.superposerCartes(rouge4, color));
        color.setChoosed(Color.RED);

        assertTrue("Peut poser car couleur choisie", Partie.superposerCartes(rouge4, color));
        assertFalse("Peut pas poser plus2", Partie.superposerCartes(plus2jaune, color));
        assertTrue("Peut poser plus2", Partie.superposerCartes(plus2rouge, color));
        assertTrue("Peut poser plus4", Partie.superposerCartes(plus4, color));
        assertFalse("Peut pas poser skip", Partie.superposerCartes(skipjaune, color));
        assertTrue("Peut poser skip", Partie.superposerCartes(skiprouge, color));
        assertFalse("Peut pas poser reverse", Partie.superposerCartes(reversejaune, color));
        assertTrue("Peut poser reverse", Partie.superposerCartes(reverserouge, color));
        assertTrue("Peut poser color", Partie.superposerCartes(color2, color));
    }

}
