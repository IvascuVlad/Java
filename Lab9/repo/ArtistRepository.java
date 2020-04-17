package repo;

import entity.AlbumsEntity;
import entity.ArtistsEntity;
import util.PersistenceUtile;

import javax.persistence.EntityManager;

public class ArtistRepository {
    PersistenceUtile pu;

    public ArtistRepository(PersistenceUtile pu) {
        this.pu = pu;
    }

    public void create(int id,String name, String country){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        ArtistsEntity artistsEntity = new ArtistsEntity();
        artistsEntity.setId(id);
        artistsEntity.setName(name);
        artistsEntity.setCountry(country);
        em.persist(artistsEntity);
        em.getTransaction().commit();
        em.close();
        pu.emf.close();
    }

    public ArtistsEntity findById(int id){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        ArtistsEntity artistsEntity = em.find(ArtistsEntity.class,id);
        return artistsEntity;
    }

    public ArtistsEntity findByName(String name){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        ArtistsEntity artistsEntity = em.find(ArtistsEntity.class,name);
        return artistsEntity;
    }
}
