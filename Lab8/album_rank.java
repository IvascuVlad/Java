public class album_rank{
    int album = 0;
    int rank = 0;

    public album_rank(int album, int rank) {
        this.album = album;
        this.rank = rank;
    }

    public int getOrder(){
        return this.rank;
    }

    @Override
    public String toString() {
        return "album_rank{" +
                "album=" + album +
                ", rank=" + rank +
                '}';
    }
}
