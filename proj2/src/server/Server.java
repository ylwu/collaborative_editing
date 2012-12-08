package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import FileSystem.FileSystem;



public class Server {
    private final ServerSocket serverSocket;
    /** True if the server should disconnect a client after a BOOM message. */
    private int numPlayers;
	public FileSystem controller;
	public List<serverThread> threadlist;

    public Server(int port, FileSystem c) throws IOException {
        serverSocket = new ServerSocket(port);
        this.numPlayers=0;
        this.controller=c;
        threadlist = new ArrayList<serverThread>();
    }

    
    public synchronized void increaseNumPlayers(){
        this.numPlayers++;
    }
    
    public synchronized void decreaseNumPlayers(){
        this.numPlayers--;
    }
    
    public synchronized int getNumPlayers() {
        return numPlayers;
    }
    
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            Socket socket = serverSocket.accept();

            // handle the client
            serverThread thread = new serverThread(this,socket, controller);
            threadlist.add(thread);
            thread.start();  
        }
    }
    
    public static void runServer(int port, FileSystem c)
            throws IOException
    {

        Server server = new Server(port, c);
        server.serve();
    }
    
    
	public static void main(final String[] args) {
		FileSystem c=new FileSystem();
        final int port=4441;
        try {
            runServer(port,c);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
