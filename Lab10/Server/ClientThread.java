import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientThread extends Thread{
    boolean rulare = true;
    private Socket socket = null ;
    private List<Game> games;
    public ClientThread (Socket socket, List<Game> lista) {
        this.socket = socket ;
        this.games = Collections.synchronizedList(lista);
    }

    public void run () {
        try {
            String request = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!request.equals("exit") && rulare) {
                request = in.readLine();
                if (request.equals("stop")) {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "Server stopped";
                    out.println(raspuns);
                    out.flush();
                    System.exit(0);
                }else if(request.equals("exit")){
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    String raspuns = "La revedere";
                    out.println(raspuns);
                    out.flush();
                }
                else {
                    System.out.println("");
                    System.out.println("Am primit " + request);
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    /* verific jocurile disponibile si care nu sunt terminate*/
                    if( request.equals("available_games"))
                    {
                        String raspuns = "";
                        if(games.size() > 0) {
                            for (int i = 0; i < games.size(); i++) {
                                if(!games.get(i).itsOver) {
                                    raspuns += "Jocul cu id-ul = ";
                                    raspuns += games.get(i).id;
                                    raspuns += " ";
                                }
                            }
                        }
                        else{
                            raspuns = "Nu sunt jocuri disponibile";
                        }
                        if(raspuns.equals(""))
                            raspuns = "Nu sunt jocuri disponibile";
                        out.println(raspuns);
                        out.flush();
                    }else if( request.equals("create_game") )
                    {
                        String raspuns;
                        /* adaug un nou joc in lista sincronizata*/
                        synchronized (this) {
                            games.add(new Game(games.size() + 1, socket.getPort()));
                        }
                        /*cand creezi jocul esti pus automat ca player1*/
                        raspuns = "S-a creat jocul " + games.size();
                        out.println(raspuns);
                        out.flush();
                    } else if ( request.substring(0,9).equals("join_game"))
                    {
                        String raspuns;
                        int i = Integer.parseInt(request.substring(10));
                        /*te poti inscre intr-un joc doar ca player2 si daca nu s-a terminat*/
                        if (games.get(i - 1).joinGame(socket.getPort()) && !games.get(i - 1).itsOver)
                            raspuns = "Ai fost adaugat la jocul cu id " + i;
                        else
                            raspuns = "Nu poti fi adaugat in jocul acesta";
                        out.println(raspuns);
                        out.flush();
                    }else if ( request.substring(0,11).equals("submit_move"))
                    {
                        for (Game game : games) {
                            /*caut in lista jocul la care esti inscris*/
                            if(game.getPlayer1().getPlayerPort() == socket.getPort() || game.getPlayer2().getPlayerPort() == socket.getPort()){
                                Player you;
                                if (game.getPlayer1().getPlayerPort() == socket.getPort())
                                    you = game.getPlayer1();
                                else
                                    you = game.getPlayer2();
                                System.out.println("Playerul " + you.getPlayerPort() + " asteata sa isi inceapa tura");
                                while(game.turn != you) {
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Playerul " + you.getPlayerPort() + " se plictiseste si stie ca e tura " + game.turn.getPlayerPort());
                                }
                                if (!game.itsOver) {
                                    String coordonate = request.substring(12);
                                    int x = Integer.parseInt(coordonate.substring(0, coordonate.indexOf("_")));
                                    int y = Integer.parseInt(coordonate.substring(coordonate.indexOf("_") + 1));
                                    System.out.println(x + " " + y);
                                    /*bucla pana cand se introduc coordonate valide*/
                                    while (!game.submitMove(x, y, socket.getPort(),you)) {
                                        out.println("Coordonate_invalide");
                                        out.flush();

                                        System.out.println("Procesez x si y");
                                        request = in.readLine();
                                        x = Integer.parseInt(request.substring(0, request.indexOf("_")));
                                        y = Integer.parseInt(request.substring(request.indexOf("_") + 1));
                                    }
                                    if (game.checkOver()) {
                                        game.itsOver = true;
                                        if (game.getPlayer1().getPlayerPort() == socket.getPort())
                                            game.winner = game.player1;
                                        else
                                            game.winner = game.player2;
                                        String raspuns = "Felicitari ai castigat";
                                        System.out.println("La revedere campionule !");
                                        game.turn = null;
                                        rulare = false;

                                        /*creaza fisierul ToHTML.html care contine o reprezentare a jocului
                                        si il incarca prin sftp la adresa specificata*/
                                        new ToHTML(game);

                                        out.println(raspuns);
                                        out.flush();
                                        //request = "exit";
                                    } else {
                                        System.out.println("Coordonate bune adaug piesa " + game.board.getBoard()[x][y]);
                                        game.turn = null;
                                        String raspuns = "Piesa adaugata";
                                        out.println(raspuns);
                                        out.flush();
                                    }
                                    game.changeTurn(you);
                                } else {
                                    String raspuns = "Ne parare rau dar ai pierdut";
                                    System.out.println("La revedere jucatorule!");
                                    game.turn = null;
                                    rulare = false;

                                    out.println(raspuns);
                                    out.flush();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }

}
