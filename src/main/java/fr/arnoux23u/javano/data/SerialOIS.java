package fr.arnoux23u.javano.data;

import java.io.*;

/**
 * Class that represents a serializable object input stream.
 *
 * @author arnoux23u
 */
public class SerialOIS extends ObjectInputStream implements Serializable {

    /**
     * Public constructor
     *
     * @param in ObjectInputStream
     * @throws IOException Exception
     */
    public SerialOIS(InputStream in) throws IOException {
        super(in);
    }
}
