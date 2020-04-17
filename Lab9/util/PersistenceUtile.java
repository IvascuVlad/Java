package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtile {
    private static PersistenceUtile single_instance = null;

    public EntityManagerFactory emf = null;

    private PersistenceUtile(){
        this.emf = Persistence.createEntityManagerFactory("MusicAlbumsPU");
    }

    public static PersistenceUtile getInstance(){
        if(single_instance == null)
            single_instance = new PersistenceUtile();
        return single_instance;
    }
}
