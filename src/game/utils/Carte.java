package game.utils;

import java.awt.*;
import java.io.Serializable;

/**
 * Classe modelisant une carte
 */
public class Carte implements Serializable {

    private static final long serialVersionUID = 45L;

    public final Color color;

    public final int value;

    public Carte(Color color, int value) {
        this.color = color;
        this.value = value;
    }
}
