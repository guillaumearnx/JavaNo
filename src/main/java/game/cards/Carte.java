package main.java.game.cards;

import main.java.datatransfer.SerialImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

import static main.java.mvc.model.Partie.getNameFromColor;

/**
 * Classe modelisant une carte
 */
public class Carte implements Serializable {

    @Serial
    private static final long serialVersionUID = 45L;

    public static final String assetsDirectory = "src" + File.separator + "main/java/game/cards" + File.separator;

    public final Color color;

    private Color choosed;

    public final int value;

    public final SerialImage image;

    public TypesCartes type;

    public Carte(Color color, int value) {
        this.color = color;
        this.value = value;
        switch (value) {
            case 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 -> {
                this.type = TypesCartes.NORMAL;
            }
            case 92, 94 -> {
                this.type = TypesCartes.ADD;
            }
            case 40, 96, 33 -> {
                this.type = TypesCartes.SPECIAL;
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

    public boolean isChoosed() {
        return this.choosed != null;
    }
}
