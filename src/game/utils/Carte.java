package game.utils;

import datatransfer.SerialImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

/**
 * Classe modelisant une carte
 */
public class Carte implements Serializable {

    private static final long serialVersionUID = 45L;
    public static final String assetsDirectory = "src"+ File.separator+"cards"+File.separator;

    public final Color color;

    public final int value;

    public final SerialImage image;

    public Carte(Color color, int value, SerialImage img) {
        this.image = img;
        this.color = color;
        this.value = value;
    }

    public static String getStringFromValue(int value) {
        return switch (value) {
            case 33 -> "COLOR";
            case 40 -> "SKIP";
            case 96 -> "loop";
            case 94 -> "+4";
            case 92 -> "+2";
            default -> String.valueOf(value);
        };
    }
}
