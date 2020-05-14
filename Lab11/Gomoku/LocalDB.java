package com.example.demo.Gomoku;

import com.example.demo.Game;
import com.example.demo.Player;

import java.util.UUID;

/*clasa folosita la desfasurarea unui game*/
public class LocalDB {
    Game game;
    Board board;
    UUID turn;

    public LocalDB(Game game, Board board) {
        this.game = game;
        this.board = board;
    }

    public boolean submitMove(int x, int y, int port){
        return board.validMove(x, y,port);
    }

    public boolean checkOver(){
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[0].length; j++) {
                /*verific pentru fiecare valoare din matrice daca s-au format 5 consecutive in cele 4 directii posibile*/
                if( (board.horizontalCheck(i,j) || board.leftobliqueCheck(i,j) || board.rightobliqueCheck(i,j) || board.verticalCheck(i,j))  && board.getBoard()[i][j]!=0) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void changeTurn(UUID you) {
        if( you == game.getPlayer1() )
            turn = game.getPlayer2();
        else
            turn= game.getPlayer1();
        notifyAll();
    }

    public void setTurn(UUID turn) {
        this.turn = turn;
    }
}
