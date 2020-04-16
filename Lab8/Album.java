import com.github.javafaker.Faker;

import java.util.Random;

public class Album {
    Database database;
    String name;
    Artist artist;
    int release_year;
    Faker faker = new Faker();
    Random random = new Random();

    public Album(Database database, Artist artist) {
        this.database = database;
        this.name = faker.cat().name();
        this.artist = artist;
        this.release_year = random.nextInt(20)+2000;
    }

    /*Stocheaza in baza de date albumul*/
    public void to_database(){
        ArtistController artistController = new ArtistController(database);
        AlbumController albumController = new AlbumController(database);
        albumController.create(this.name,artistController.findByName(this.artist.name),this.release_year);
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", " + artist +
                ", release_year=" + release_year +
                '}';
    }
}
