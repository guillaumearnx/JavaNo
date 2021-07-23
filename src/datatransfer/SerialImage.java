package datatransfer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerialImage implements Serializable {

    transient List<BufferedImage> onlyImage;

    public SerialImage(BufferedImage img){
        onlyImage = new ArrayList<BufferedImage>(){{
            add(img);
        }};
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
            ImageIO.write(onlyImage.get(0), "png", oos);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        onlyImage.add(ImageIO.read(ois));
    }

    public Image getImage() {
        return onlyImage.get(0);
    }
}
