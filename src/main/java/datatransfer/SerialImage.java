package main.java.datatransfer;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.*;

public class SerialImage implements Serializable {

    private byte[] imgArray;
    public SerialImage(BufferedImage img) throws IOException {
        ByteArrayOutputStream boos = new ByteArrayOutputStream();
        ImageIO.write(img, "png", boos);
        imgArray = boos.toByteArray();
    }

    public BufferedImage toImage() {
        try {
            return ImageIO.read(new ByteArrayInputStream(imgArray));
        } catch (IOException exception) {
            System.err.println("Error while creating image from bytes");;
            exception.printStackTrace();
            return null;
        }
    }


}
