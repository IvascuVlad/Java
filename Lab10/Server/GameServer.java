import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameServer{
    // Define the port on which the server is listening
    public static final int PORT = 8100;
    public GameServer() throws IOException {
        ServerSocket serverSocket = null ;
        List <Game> listaJocurilor = Collections.synchronizedList(new ArrayList<>());
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                ClientThread clientThread = new ClientThread(socket,listaJocurilor);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err. println ("Ooops... " + e);
        } finally {
            serverSocket.close();
        }
    }
    public static void main ( String [] args ) throws IOException {
        GameServer server = new GameServer ();
    }

}
