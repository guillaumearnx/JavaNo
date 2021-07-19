package network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ThreadServer extends Thread{
    private Socket socket;
    private ArrayList<ThreadServer> threadlist;
    private PrintWriter output;

    public ThreadServer(Socket socket, ArrayList<ThreadServer> threads){
        threadlist = threads;
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                String outString = input.readLine();
                if(outString.equalsIgnoreCase("exit"))
                    break;
                printToAll(outString);
                System.out.println("Server received : "+outString);
            }
        }catch (IOException e){
            System.err.println("Erreur avec le thread : "+e);
            System.exit(-1);
        }
    }

    private void printToAll(String toPrint){
        for(ThreadServer th : threadlist){
            th.output.println(toPrint);
        }
    }
}
