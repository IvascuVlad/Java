import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GameClient {
    public static void main (String[] args) throws IOException {
        boolean rulare = true;
        String serverAddress = "127.0.0.1"; // The server's IP address
        int PORT = 8100; // The server's port
        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream())) ) {
            // Send a request to the server
            String request = "";
            String response = "";
            do {
                Scanner keyboard = new Scanner(System.in);
                System.out.println("");
                System.out.println("Write a command (exit, stop, available_games, create_game ");
                System.out.println("join_game_(id-ul meciului dorit), submit_move_(coordonata x)_(coordonata y)");
                request = keyboard.next();
                out.println(request);
                // Wait the response from the server ("Hello World!")
                response = in.readLine ();
                while ( response.equals("Coordonate_invalide"))
                {
                    System.out.println("Introduceti alte coordonate de forma x_y");
                    request = keyboard.next();
                    out.println(request);

                    response = in.readLine();
                }
                System.out.println(response);
                if(response.equals("Felicitari ai castigat") || response.equals("Ne parare rau dar ai pierdut"))
                    rulare = false;
                if(request.equals("exit") || request.equals("stop"))
                    rulare = false;
            }while(rulare);
        } catch (UnknownHostException e) {
            System.err.println("No server listening... " + e);
        }
    }

}
