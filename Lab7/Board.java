import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    int n;
    Token [] list;
    private int playerTurn = 1;

    @Override
    public String toString() {
        return "Board{" +
                "list=" + Arrays.toString(list) +
                '}';
    }

    public Board(int number) {
        n=number;
        list = new Token[n];
        for (int i = 0; i < n; i++) {
            list[i]=new Token(i+1);
        }
    }

    public void printList(){
        for (Token token : list) {
            System.out.println(token.number);
        }
    }

    public Token[] getList() {
        return list;
    }

    /*functia valideaza un token generat random de player*/
    public synchronized boolean validateToken(int contor){
        if(contor > 0 ){
            for (Token token : list) {
                if(token.number == contor) {
                    notifyAll();
                    return true;
                }
            }
        }
        return false;
    }

    /* functia acceseaza boardul utilizand un semafor asupra resursei comune si extrage tokenul lasand in locul lui un token null */
    public synchronized boolean getToken(int playerId, int contor){
        while(playerTurn != playerId){
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        for (Token token : list) {
            if(token.number == contor) {
                token.number = 0;
                playerTurn=(playerTurn+1) % 3;
                notifyAll();
                return true;
            }
        }
        return false;
    }
}
