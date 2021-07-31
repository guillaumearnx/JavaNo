package fr.arnoux23u.javano.game.utils;

import fr.arnoux23u.javano.cards.Cartes;
import fr.arnoux23u.javano.datatransfer.SerialImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import static fr.arnoux23u.javano.game.Partie.getNameFromColor;

/**
 * Classe modelisant une carte
 */
public class Carte implements Serializable {

    private static final long serialVersionUID = 45L;
    public static final String assetsDirectory = "src" + File.separator + "fr/arnoux23u/javano/cards" + File.separator;

    public final Color color;

    private Color choosed;

    public final int value;

    public final SerialImage image;

    public Cartes type;

    private boolean special;

    public Carte(Color color, int value) {
        this.color = color;
        this.value = value;
        switch (value) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                this.type = Cartes.NORMAL;
                this.special = false;
            }
            case 92 -> {
                this.type = Cartes.ADD;
                this.special = false;
            }
            case 40, 96 -> {
                this.type = Cartes.SPECIAL;
                this.special = false;
            }
            case 94 -> {
                this.type = Cartes.ADD;
                this.special = true;
            }
            case 33 -> {
                this.type = Cartes.SPECIAL;
                this.special = true;
            }
        }
        SerialImage image;
        try {
            image = new SerialImage(ImageIO.read(new File(Carte.assetsDirectory + getNameFromColor(color) + File.separator, value + ".png")));
        } catch (IOException e) {
            image = null;
            System.out.println("Cant read texture for card : " + value);
        }
        this.image = image;

    }

    public void setChoosed(Color choosed) {
        this.choosed = choosed;
    }

    public Color getChoosed() {
        return choosed;
    }

    public boolean isSpecial() {
        return special;
    }

    public boolean isChoosed() {
        return this.choosed != null;
    }
}
