public class Timekeeper implements Runnable{
    Game game;
    long startTime = -1;
    long endTime;
    boolean running = true;

    public Timekeeper(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        while(game.gameState==0){
            if(startTime == -1)
                startTime = System.currentTimeMillis();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endTime = System.currentTimeMillis();
            game.totalTime  = (endTime - startTime) * 0.001;
            if(game.totalTime > game.expectedTime)
                game.gameState=3;
        }
    }
}
