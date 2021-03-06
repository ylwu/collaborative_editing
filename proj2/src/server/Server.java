package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import FileSystem.FileSystem;


/**
 * 
 * This is the server for our collaborative editor. 
 * 
 * (This server has been implemented with a reference to Problem 
 * Set 3: Multiplayer Minesweeper)
 *
 */
public class Server {
    private final ServerSocket serverSocket;
    private int numPlayers;
	public FileSystem fileSystem;
	public List<serverThread> threadlist;

    public Server(int port, FileSystem c) throws IOException {
        serverSocket = new ServerSocket(port);
        this.numPlayers=0;
        this.fileSystem=c;
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
            serverThread thread = new serverThread(this,socket, fileSystem);
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
    
}
