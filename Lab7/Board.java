class Board {
    Game game;
    int n;
    Token [] list;
    public int playerTurn=0;

    public Board(Game game,int number) {
        this.game = game;
        n=number;
        list = new Token[n];
        for (int i = 0; i < n; i++) {
            list[i]=new Token(i+1);
        }
    }

    public Token[] getList() {
        return list;
    }

    public synchronized boolean validateToken(int contor, int playerId){
        while(playerTurn != playerId){
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        if(contor > 0 ){
            for (Token token : list) {
                if(token.number == contor) {
                    System.out.println(playerId+": Am gasit tokenul " + contor);
                    notifyAll();
                    return true;
                }
            }
        }
        notifyAll();
        return false;
    }

    public synchronized boolean getToken(int playerId, int contor){
        while(playerTurn != playerId){
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        playerTurn=(playerTurn+1)%3;
        System.out.println(playerId+": Este tura mea");
        for (Token token : list) {
            if(token.number == contor) {
                token.number = 0;
                System.out.println(playerId+": Mi-am extras tokenul "+contor);
                notifyAll();
                return true;
            }
        }
        System.out.println(playerId+ ": Urmeaza :" + playerTurn);
        notifyAll();
        return false;
    }

    /*functie folosita de smart player verifica progresiile celorlati si in caz ca playerii mai au un token si castiga el le ia acel token*/
    public synchronized int checkOthers(int playerId){
        while(playerTurn != playerId){
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println(playerId + ": Verific un player");
        for (Player player : game.players) {
            System.out.println(playerId+": Intru in for de players");
            if(player.progressionSize() == game.k-1 && player.getId()!=playerId){
                System.out.println(playerId+": Asta e " + player.getName());
                int ratio = player.getTokens().get(1).number-player.getTokens().get(0).number;
                int progressionLength = 1;
                for (int i = 0; i < player.getTokens().size()-1; i++) {
                    System.out.println(playerId+": Ii verific tokenii");
                    if(player.getTokens().get(i+1).number - player.getTokens().get(i).number == ratio)
                        progressionLength++;
                    else{
                        ratio = player.getTokens().get(i+1).number - player.getTokens().get(i).number;
                        progressionLength = 2;
                    }
                    if (progressionLength == game.k-1) {
                        System.out.println(playerId + ": L-am gasit");
                        notifyAll();
                        if(list[player.getTokens().get(i + 1).number + ratio].number != 0)
                            return (list[player.getTokens().get(i + 1).number - 1 + ratio].number);
                    }
                }
            }
        }
        System.out.println(playerId + ": Nu l-am gasit");
        notifyAll();
        return 0;
    }

}
