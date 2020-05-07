public class Game {
    boolean itsOver = false;
    int id;
    Board board;
    Player player1;
    Player player2;
    Player winner;

    public Game(int id, int playerPort) {
        this.board = new Board(15);
        this.id = id;
        this.player1 = new Player(playerPort);
    }

    public boolean joinGame(int playerPort){
        if(player2 == null && player1.getPlayerPort() !=playerPort) {
            this.player2 = new Player(playerPort);
            return true;
        }
        return false;
    }

    public boolean submitMove(int x, int y, int port){ return board.validMove(x, y,port); }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
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
}
