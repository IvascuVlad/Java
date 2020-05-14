package com.example.demo.Gomoku;

import com.example.demo.Controller.GameController;
import com.example.demo.Controller.PlayerController;
import com.example.demo.Game;
import com.example.demo.GameNotFound;
import com.example.demo.Player;

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
    List<LocalDB> localDBS;
    PlayerController playerController = new PlayerController();
    GameController gameController = new GameController();
    Player player;
    public ClientThread (Socket socket, List<LocalDB> localDBList) {
        this.socket = socket ;
        this.localDBS = Collections.synchronizedList(localDBList);
    }

    public void run () {
        try {
            player = playerController.createPlayerController("Dorel");
            System.out.println("Ai intrat si te-am bagat " + player.getName());
            String request = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!request.equals("exit") && rulare) {
                List<Game> games = gameController.getGamesController();
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
                        if(localDBS.size() > 0){
                            System.out.println("Asta din local DB");
                            for (int i = 0; i < localDBS.size(); i++) {
                                if(!localDBS.get(i).game.itsOver){
                                    raspuns += "Jocul cu id-ul = ";
                                    raspuns += i;
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
                    }else if( request.equals("create_game") ) {
                        String raspuns;
                        Game result = gameController.createGameController();
                        if (result != null) {
                            raspuns = "S-a creat jocul " + games.size();
                            synchronized (this) {
                                localDBS.add(new LocalDB(result, new Board(16)));
                            }
                        }
                        else
                            raspuns = "Nu s-a putut crea jocul";
                        out.println(raspuns);
                        out.flush();
                    } else if ( request.substring(0,9).equals("join_game")) {
                        String raspuns;
                        int i = Integer.parseInt(request.substring(10));
                        if (gameController.joinGameController(localDBS.get(i).game.getId(),player.getId()) && !localDBS.get(i).game.itsOver) {
                            raspuns = "Ai fost adaugat la jocul cu id " + i;
                            if(localDBS.get(i).game.getPlayer1() == null) {
                                localDBS.get(i).game.setPlayer1(player.getId());
                                localDBS.get(i).setTurn(player.getId());
                            }
                            else
                                localDBS.get(i).game.setPlayer2(player.getId());
                        }
                        else
                            raspuns = "Nu poti fi adaugat in jocul acesta";
                        out.println(raspuns);
                        out.flush();
                    }else if ( request.substring(0,11).equals("submit_move"))
                    {
                        for (LocalDB localDB : localDBS) {
                            if(localDB.game.getPlayer1().equals(player.getId()) || localDB.game.getPlayer2().equals(player.getId())){
                                System.out.println("Playerul " + player.getName() + " asteata sa isi inceapa tura");
                                while(localDB.turn != player.getId()) {
                                    try {
                                        Thread.sleep(5000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    System.out.println("Playerul " + player.getId() + " se plictiseste si stie ca e tura " + localDB.turn);
                                }
                                if (!localDB.game.itsOver) {
                                    String coordonate = request.substring(12);
                                    int x = Integer.parseInt(coordonate.substring(0, coordonate.indexOf("_")));
                                    int y = Integer.parseInt(coordonate.substring(coordonate.indexOf("_") + 1));
                                    System.out.println(x + " " + y);
                                    /*bucla pana cand se introduc coordonate valide*/
                                    while (!localDB.submitMove(x, y, socket.getPort())) {
                                        out.println("Coordonate_invalide");
                                        out.flush();

                                        System.out.println("Procesez x si y");
                                        request = in.readLine();
                                        x = Integer.parseInt(request.substring(0, request.indexOf("_")));
                                        y = Integer.parseInt(request.substring(request.indexOf("_") + 1));
                                    }
                                    if (localDB.checkOver()) {
                                        localDB.game.setItsOver(true);
                                        gameController.joinGameController(localDB.game.getId(),player.getId());
                                        localDBS.remove(localDB);
                                        String raspuns = "Felicitari ai castigat";
                                        System.out.println("La revedere campionule !");
                                        localDB.turn = null;
                                        rulare = false;
                                        out.println(raspuns);
                                        out.flush();
                                        //request = "exit";
                                    } else {
                                        System.out.println("Coordonate bune adaug piesa " + localDB.board.getBoard()[x][y]);
                                        localDB.turn = null;
                                        String raspuns = "Piesa adaugata";
                                        out.println(raspuns);
                                        out.flush();
                                    }
                                    localDB.changeTurn(player.getId());
                                } else {
                                    String raspuns = "Ne parare rau dar ai pierdut";
                                    System.out.println("La revedere jucatorule!");
                                    localDB.turn = null;
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
        } catch (IOException | GameNotFound e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close(); // or use try-with-resources
            } catch (IOException e) { System.err.println (e); }
        }
    }

}

