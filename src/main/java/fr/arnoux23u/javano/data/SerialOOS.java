package fr.arnoux23u.javano.data;

import java.io.*;

/**
 * Class that represents a serializable object output stream.
 *
 * @author arnoux23u
 */
public class SerialOOS extends ObjectOutputStream implements Serializable {

    /**
     * Public constructor
     *
     * @param out ObjectOutputStream
     * @throws IOException Exception
     */
    public SerialOOS(OutputStream out) throws IOException {
        super(out);
    }
}
