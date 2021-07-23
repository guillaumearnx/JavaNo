package datatransfer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public class SerialImage extends BufferedImage implements Serializable {

    private static final long serialVersionUID = 18L;

    public SerialImage(File readFile) throws IOException {
        this(ImageIO.read(readFile));
    }

    public SerialImage(BufferedImage image){
        this(image.getColorModel(), image.getRaster());
    }

    public SerialImage(ColorModel cm, WritableRaster wr){
        super(cm, wr, cm.isAlphaPremultiplied(), null);
    }
}
