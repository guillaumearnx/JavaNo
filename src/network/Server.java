package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ArrayList<ThreadServer> threadlist;

    public Server() {

        threadlist = new ArrayList<ThreadServer>();

        final int PORT = 6587;
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Serveur écoute sur le port : "+serverSocket.getLocalPort());
            Socket socket = serverSocket.accept();
            ThreadServer thread = new ThreadServer(socket, threadlist);
            threadlist.add(thread);
            thread.start();
            /*new Thread(()->{
                try{

                }catch (IOException e){
                    System.out.println("Error when creating thread ... : "+e);
                    System.exit(-1);
                }
            }).start();*/
        }catch (IOException e){
            System.err.println("Creation de socket impossible : "+e);
            System.exit(-1);
        }

        /*try{
            Socket client;
            while (true){
                client = serverSocket.accept();
                ThreadClient t = new ThreadClient(client);
                t.start();
            }
        }catch (IOException e){
            System.err.println("Erreur pendant l'attente d'une connexion : "+e);
            System.exit(-1);
        }

        try{
            serverSocket.close();
        }catch (IOException e){
            System.err.println("Erreur lors de la fermeture du socket : "+e);
            System.exit(-1);
        }

        /*try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT + " ...");
            System.out.println("Waiting for clients ...");
            while (true){
                Socket client = serverSocket.accept();
                String clientIP = client.getInetAddress().toString() ;
                int clientPort = client.getPort();
                System.out.format("[Serveur] : Arrivée du client IP %s sur le port %d\n", clientIP, clientPort);
                System.out.format("[Serveur ]: Creation du thread T_%d\n" , clientPort);
                new Thread( new ThreadClient(client , "T_" +clientPort)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
        









        /*Thread t1 = new Thread(() -> {
            try {
                while (true) {
                    Socket socket = serverSocket.accept();
                    p.ajouterJoueur(String.valueOf(socket.getInetAddress()));
                    System.out.println("Client connected : ip : "+socket.getInetAddress());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    Scanner in = new Scanner(socket.getInputStream());
                    while (in.hasNextLine()) {
                        String input = in.nextLine();
                        if (input.equalsIgnoreCase("exit"))
                            break;
                        System.out.println("Received from client : " + input);
                        out.println("Im the server and i receive " + input);
                    }
                }
            } catch (IOException ignored) {
            }
        });
        t1.start();
    }*/


}
