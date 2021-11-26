package fr.arnoux23u.javano.data;

import java.io.*;
import java.net.*;

/**
 * Class that represents a serializable socket
 *
 * @author arnoux23u
 */
public class SerialSocket extends Socket implements Serializable {

    /**
     * Public constructor with two parameters
     *
     * @param host hostname
     * @param port port
     * @throws IOException Exception when connection fails
     */
    public SerialSocket(InetAddress host, int port) throws IOException {
        super(host, port);
    }

}
