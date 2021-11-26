package fr.arnoux23u.javano.data;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

/**
 * Class that represents a serializable image.
 *
 * @author arnoux23u
 */
public class SerialImage implements Serializable {

    private final byte[] imgArray;

    /**
     * Public constructor
     *
     * @param img The image to serialize.
     */
    public SerialImage(BufferedImage img) throws IOException {
        ByteArrayOutputStream boos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", boos);
        imgArray = boos.toByteArray();
    }

    /**
     * Method who returns the image
     *
     * @return image serialized
     */
    public BufferedImage toImage() {
        try {
            return ImageIO.read(new ByteArrayInputStream(imgArray));
        } catch (IOException exception) {
            System.err.println("Error while creating image from bytes");
            return null;
        }
    }

}
