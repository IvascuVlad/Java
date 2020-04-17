package app;

import repo.AlbumRepository;
import repo.ArtistRepository;
import util.PersistenceUtile;

public class Main {
    public static void main(String[] args){
        PersistenceUtile pu = PersistenceUtile.getInstance();

        AlbumRepository albumRepository = new AlbumRepository(pu);
        ArtistRepository artistRepository = new ArtistRepository(pu);

        artistRepository.create(1,"Ivascu","Romania");
        albumRepository.create(1,"Vlad",1,2020);

        System.out.println(artistRepository.findById(1));
        System.out.println(artistRepository.findByName("Ivascu"));

        System.out.println(albumRepository.findById(1));
        System.out.println(albumRepository.findByName("Vlad"));
        System.out.println(albumRepository.findByArtist(1));
    }
}

