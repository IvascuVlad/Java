import java.util.concurrent.Semaphore;

public class Game {
    double totalTime;
    double expectedTime;
    int k;
    int gameState = 0;
    Player [] players = new Player[3];
    Board board;

    Semaphore semaphore = new Semaphore(3, true);

    public Game(int m,int k, int expectedTime) {
        board = new Board(this,m);
        this.expectedTime = expectedTime;
        this.k = k;
        players[0] = new SmartPlayer(0, this, "Abdaluh Smart");
        players[1] = new RandomPlayer(1, this, "Cantemir Random");
        players[2] = new ManualPlayer(2, this, "Jan Manual");
    }

    public void startGame() throws InterruptedException {
        Runnable runnable0 = new Timekeeper(this);
        Runnable runnable1 = (Runnable) players[0];
        Runnable runnable2 = (Runnable) players[1];
        Runnable runnable3 = (Runnable) players[2];
        Thread t0 = new Thread(runnable0);
        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        t0.setDaemon(true);

        t0.start(); t1.start(); t2.start(); t3.start();
        //System.out.println(semaphore.toString());
        t0.join(); t1.join(); t2.join(); t3.join();
        results();
    }

    public void results(){
        for (Player player : players) {
            if(totalTime > expectedTime)
            {
                System.out.println("Timpul a expirat " + player.getName() + " mult succes tura viitoare");
            }
            else {
                if (player.progressionSize() >= k && gameState == 2)
                    System.out.println("Felicitari " + player.getName() + " ai castigat " + player.progressionSize() + " puncte! ");
                else if (player.progressionSize() < k && gameState == 2)
                    System.out.println("Imi pare rau " + player.getName() + " ai pierdut cu progresia de lungime " + player.progressionSize() + ".");
                else
                    System.out.println("It's Over " + player.getName() + " ai castigat " + player.progressionSize() + " puncte!");
                System.out.println(player.getTokens().toString());
            }
        }
        System.out.println("Durata jocului: " + totalTime + " secunde!");
    }
}
