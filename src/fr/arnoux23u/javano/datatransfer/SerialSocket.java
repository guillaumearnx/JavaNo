package fr.arnoux23u.javano.datatransfer;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class SerialSocket extends Socket implements Serializable {
    public SerialSocket(InetAddress host, int port) throws IOException {
        super(host, port);
    }


}
