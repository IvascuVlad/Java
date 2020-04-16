import com.github.javafaker.Faker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database database =  Database.getInstance();
        /*ArtistController artistController = new ArtistController(database);
        AlbumController albumController = new AlbumController(database);
        artistController.create("Alexandru","Rusia");
        artistController.findByName("Alexandru");
        albumController.create("Muzica",1,2020);
        albumController.findByArtist(1);*/
        /*Creez un artist random*/
        Artist artist = new Artist(database);
        System.out.println(artist.toString());
        artist.to_database();

        /*Creez un album random*/
        Album album1 = new Album(database,artist);
        System.out.println(album1.toString());
        album1.to_database();

        Album album2 = new Album(database,artist);
        System.out.println(album2.toString());
        album2.to_database();

        /*Creez un chart random*/
        List<Album> charts = new ArrayList<Album>();
        charts.add(album1);
        charts.add(album2);
        Charts charts1 = new Charts(database,"Top Romania",charts);
        System.out.println(charts1.toString());
        charts1.to_database();
        charts1.artistsRanking();

    }
}
