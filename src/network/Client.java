package network;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        final String HOST = "127.0.0.1";
        final int PORT = 6587;
        System.out.println("Started client");

        try (Socket socket = new Socket(HOST, PORT)){
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            String userInput;
            String response;
            String name = "";
            ThreadClient threadClient = new ThreadClient(socket);
            threadClient.start();
            do{
                if(name.equals("")) {
                    System.out.println("Enter your name : ");
                    userInput = sc.nextLine();
                    name = userInput;
                    output.println(userInput);
                    if (userInput.equalsIgnoreCase("exit"))
                        break;
                }else {
                    String message = ("("+name+")"+" messsage : ");
                    System.out.println(message);
                    userInput = sc.nextLine();
                    output.println(userInput);
                    if(userInput.equalsIgnoreCase("exit"))
                        break;
                }
            }while (!userInput.equalsIgnoreCase("exit"));

        }catch (IOException e){
            System.err.println("Exception in main : "+ e);
        }









        /*Socket socket = null;
        try{
            socket = new Socket(HOST, PORT);
        }catch (UnknownHostException e){
            System.err.println("Erreur avec l'hote : "+e);
            System.exit(-1);
        }catch (IOException e){
            System.err.println("Création de socket impossible : "+e);
            System.exit(-1);
        }

        BufferedReader input = null;
        PrintWriter output = null;
        try{
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        }catch (IOException e){
            System.out.println("Association des flux impossible : "+e);
            System.exit(-1);
        }

        // Envoi de 'Bonjour'
        String message = "Bonjour";
        System.out.println("Envoi: " + message);
        output.println(message);

        // Lecture de 'Bonjour'
        try {
            message = input.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(-1);
        }
        System.out.println("Lu: " + message);

        // Envoi de 'Au revoir'
        message = "Au revoir";
        System.out.println("Envoi: " + message);
        output.println(message);

        // Lecture de 'Au revoir'
        try {
            message = input.readLine();
        } catch(IOException e) {
            System.err.println("Erreur lors de la lecture : " + e);
            System.exit(-1);
        }
        System.out.println("Lu: " + message);

        // Fermeture des flux et de la socket
        try {
            input.close();
            output.close();
            socket.close();
        } catch(IOException e) {
            System.err.println("Erreur lors de la fermeture des flux et de la socket : " + e);
            System.exit(-1);
        }*/

    }

}

