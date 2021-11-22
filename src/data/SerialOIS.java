package data;

import java.io.*;

public class SerialOIS extends ObjectInputStream implements Serializable {

    public SerialOIS(InputStream in) throws IOException {
        super(in);
    }
}
