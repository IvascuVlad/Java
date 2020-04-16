import com.github.javafaker.Faker;

public class Artist {
    Database database;
    String name;
    String country;
    Faker faker = new Faker();

    public Artist(Database database) {
        this.database = database;
        this.name = faker.gameOfThrones().character();
        this.country = faker.gameOfThrones().city();
    }

    /*Stocheaza in baza de date artistul*/
    public void to_database(){
        ArtistController artistController = new ArtistController(database);
        artistController.create(this.name,this.country);
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
