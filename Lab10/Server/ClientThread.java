import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread {
    boolean rulare = true;
    private Socket socket = null ;
    public ClientThread (Socket socket) { this.socket = socket ; }
    public boolean run () {
        try {
            String request = "";
            while(!request.equals("exit") && rulare) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                request = in.readLine();
                if (request.equals("stop")) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "Server stopped";
                    out.println(raspuns);
                    out.flush();
                    rulare = false;
                } else {
                    System.out.println("Am primit " + request);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "Server received the request ... ";
                    out.println(raspuns);
                    out.flush();
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
        return rulare;
    }

}
