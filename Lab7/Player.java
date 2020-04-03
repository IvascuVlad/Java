import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Player implements Runnable{
    int playerId;
    Game game;
    String name;
    List<Token> tokens = new ArrayList<>();
    public boolean running = true;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Player(Game game, String name,int Id) {
        this.playerId = Id;
        this.game = game;
        this.name = name;
    }

    /*verifica daca s-au epuizat tokenii*/
    private boolean checkOver(){
        int contor = 0;
        for (Token token : game.board.getList()) {
            if(token.number == 0)
                contor++;
        }
        if(contor == game.board.list.length)
            return true;
        return false;
    }

    /*calculeaza lungimea progresiei aritmetice obtinute de jucator*/
    private int progressionSize(){
        int contor;
        int max=0;
        if(tokens.size()>1) {
            contor = tokens.get(0).number - tokens.get(1).number;
            int progressionLength = 1;
            for (int i = 0; i < tokens.size() - 1; i++) {
                if (contor == tokens.get(i + 1).number - tokens.get(i).number) {
                    progressionLength++;
                } else {
                    contor = tokens.get(i + 1).number - tokens.get(i).number;
                    if (progressionLength >= max) {
                        max = progressionLength;
                    }
                    progressionLength = 2;
                }
            }
            if (progressionLength >= max) {
                return progressionLength;
            }
            else
                return max;
        }
        return 0;
    }

    @Override
    public synchronized void  run() {
        //extract one token from board
        while(running){
            int contor = 0;
            synchronized (this) {
                do {
                    Random rand = new Random();
                    contor = rand.nextInt(game.board.n + 1);
                } while (!game.board.validateToken(contor) && game.board.getToken(playerId, contor));
            }
            //System.out.println("Player " + name + " selected token " + contor);
            tokens.add(new Token(contor));
            tokens.sort(Comparator.comparing(Token::getNumber));
            if(checkOver())/*s-au epuizat tokenii*/ {
                game.gameState = 1;
            }
            if(progressionSize() >= game.k) /*a castigat un jucator*/ {
                game.gameState = 2;
            }
            notifyAll();
            if(game.gameState > 0){
                if(progressionSize() >= game.k) { /*a obtinut o progresie aritmetica de lungime k*/
                    System.out.println("Felicitari ai castigat " + name + " cu progresia de lungime " + progressionSize());
                } else {
                    System.out.println("It's Over " + name + " cu progresia de lungime " + progressionSize());
                }
            }
            if(game.gameState > 0 ) {
                System.out.println(name + tokens.toString());
                running=false;
            }
        }
    }
}
