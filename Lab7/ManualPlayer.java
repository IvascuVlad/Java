import java.util.*;

public class ManualPlayer implements Player,Runnable {
    int playerId;
    Game game;
    String name;
    List<Token> tokens = new ArrayList<>();
    public boolean running = true;

    public ManualPlayer(int playerId, Game game, String name) {
        this.playerId = playerId;
        this.game = game;
        this.name = name;
    }

    @Override
    public boolean checkOver() {
        int contor = 0;
        for (Token token : game.board.getList()) {
            if(token.number == 0)
                contor++;
        }
        if(contor == game.board.list.length)
            return true;
        return false;
    }

    @Override
    public int progressionSize() {
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
    public String getName() {
        return name;
    }

    @Override
    public List<Token> getTokens() {
        return tokens;
    }

    @Override
    public int getId() {
        return playerId;
    }

    @Override
    public void run() {
        while(running){
            if(!checkOver() && game.gameState==0) {
                System.out.println(playerId + ": Stau sa imi incep tura ");
                if(game.gameState>0){
                    System.out.println(playerId+": M-am trezit la realitate");
                }else {
                    if (game.board.playerTurn == playerId) {
                        int contor = 0;
                        do {
                            Scanner input = new Scanner(System.in);
                            for (Token token : game.board.getList()) {
                                System.out.print(token.number + " ");
                            }
                            System.out.println("");
                            System.out.println("Introduce un token : ");
                            contor = input.nextInt();
                        } while (!game.board.validateToken(contor, playerId) && game.gameState == 0);

                        if (game.board.validateToken(contor, playerId) && !checkOver()) {
                            game.board.getToken(playerId, contor);
                            tokens.add(new Token(contor));
                            tokens.sort(Comparator.comparing(Token::getNumber));
                        }

                        if (progressionSize() >= game.k) /*a castigat un jucator*/ {
                            game.gameState = 2;
                            running = false;
                            System.out.println(playerId + ": Felicitari ai castigat " + name + " cu progresia de lungime " + progressionSize());
                        }
                    }else {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println(playerId+": Intru pe else ");
                running=false;
                if(game.gameState!=2)
                    game.gameState=1;
            }
        }
    }
}

