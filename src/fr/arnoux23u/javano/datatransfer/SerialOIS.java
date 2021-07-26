package fr.arnoux23u.javano.datatransfer;

import java.io.*;

public class SerialOIS extends ObjectInputStream implements Serializable {

    public SerialOIS(InputStream in) throws IOException {
        super(in);
    }
}
