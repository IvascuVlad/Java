import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database database =  Database.getInstance();
        ArtistController artistController = new ArtistController(database);
        AlbumController albumController = new AlbumController(database);
        artistController.create("Alexandru","Rusia");
        artistController.findByName("Alexandru");
        albumController.create("Muzica",1,2020);
        albumController.findByArtist(1);

    }
}
