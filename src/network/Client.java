package network;

import data.*;

import java.io.*;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @author arnoux23u
 */
public class Client {

    final String IP = "192.168.0.4";
    final int PORT = 6587;
    String name;
    Scanner sc;

    public Client(String name) {
        this.name = name;
        try {
            InetAddress ip = InetAddress.getByName(IP);
            SerialSocket s = new SerialSocket(ip, PORT);
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            System.out.println("Connected to server | Client -> " + name);
            oos.writeObject(name);
            sc = new Scanner(System.in);
            while (true) {
                System.out.println("Enter a command : ");
                String command = sc.nextLine();
                oos.writeObject(command);
                if (command.equals("exit")) {
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
