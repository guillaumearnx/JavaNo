package network;

import game.Partie;

import java.io.*;
import java.net.Socket;

public class ThreadClient extends Thread{

    private BufferedReader input;
    //private PrintWriter output;
    private Socket socket;

    public ThreadClient(Socket socketClient) throws IOException {
        socket = socketClient;
        input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        //output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())));
    }

    @Override
    public void run() {
        /*System.out.println("Demmarage du thread");
        String message = "";
        try{
            message = input.readLine();
        }catch (IOException e){
            System.err.println("Erreur lors de la lecture : "+e);
            System.exit(-1);
        }
        System.out.println("Lu : "+message);
        message = "Bonjour";
        System.out.println("Envoi : "+message);
        output.println(message);
        try{
            input.close();
            output.close();
            client.close();
        }catch (IOException e){
            System.err.println("Erreur lors de la fermeture des sockets : "+e);
            System.exit(-1);
        }*/
        try{
            while (true){
                String response = input.readLine();
                System.out.println(response);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                input.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
