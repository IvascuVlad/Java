public class Board {
    boolean first_time = true;
    private int [][] board;

    public Board(int dimensiune) {
        this.board = new int[dimensiune][dimensiune];
        for (int[] ints : board) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println("");
        }
    }

    public boolean validMove(int x, int y ,int port){
        if (first_time){
            first_time = false;
            board[x][y] = port;
            return true;
        }
        else{
            if(board[x][y] != 0)
                return false;
            /*nu se poate adauga o piesa daca nu are vreun vecin deja pus exceptand prima data*/
            else if(board[x-1][y-1]!=0 || board[x-1][y]!=0 || board[x][y+1]!=0 || board[x][y-1]!=0 || board[x+1][y-1]!=0 || board[x+1][y]!=0 || board[x+1][y+1]!=0){
                board[x][y] = port;
                return true;
            }
        }
        return false;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean horizontalCheck(int x, int y){
        if(y+4 >= board[0].length)
            return false;
        for (int i = 1; i < 5; i++) {
            if(board[x][y] != board[x][y+i])
                return false;
        }
        return true;
    }

    public boolean rightobliqueCheck(int x, int y){
        if(y+4 >= board[0].length || x+4 >= board.length)
            return false;
        for (int i = 1; i < 5; i++) {
            if(board[x][y] != board[x+i][y+i])
                return false;
        }
        return true;
    }

    public boolean leftobliqueCheck(int x, int y){
        if(y-4 >= board[0].length || x-4 >= board.length || y-4 < 0 || x-4 < 0)
            return false;
        for (int i = 1; i < 5; i++) {
            if(board[x][y] != board[x-i][y-i])
                return false;
        }
        return true;
    }

    public boolean verticalCheck(int x, int y){
        if(x+4 >= board.length)
            return false;
        for (int i = 1; i < 5; i++) {
            if(board[x][y] != board[x+i][y])
                return false;
        }
        return true;
    }
}
