public class Game {
    int k;
    int gameState = 0;
    Player [] players = new Player[3];
    Board board;

    public Game(int m,int k) {
        board = new Board(m);
        this.k = k;
        players[0] = new Player(this,"Abdaluh",0);
        players[1] = new Player(this,"Cantemir",1);
        players[2] = new Player(this,"Jan",2);
    }

    public void startGame(){
        Runnable runnable = players[0];
        Runnable runnable2 = players[1];
        Runnable runnable3 = players[2];
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        t1.start(); t2.start(); t3.start();
    }
}
