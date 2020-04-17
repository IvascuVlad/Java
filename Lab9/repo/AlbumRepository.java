package repo;

import entity.AlbumsEntity;
import entity.ArtistsEntity;
import util.PersistenceUtile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class AlbumRepository {
    PersistenceUtile pu;

    public AlbumRepository(PersistenceUtile pu) {
        this.pu = pu;
    }

    public void create(int id,String name, int artistId, int releaseYear){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        AlbumsEntity albumsEntity = new AlbumsEntity();
        albumsEntity.setId(id);
        albumsEntity.setArtist_id(artistId);
        albumsEntity.setName(name);
        albumsEntity.setReleaseYear((long) 2020);
        em.persist(albumsEntity);
        em.getTransaction().commit();
        em.close();
        pu.emf.close();
    }

    public AlbumsEntity findById(int id){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        AlbumsEntity albumsEntity = em.find(AlbumsEntity.class,id);
        em.getTransaction().commit();
        em.close();
        pu.emf.close();
        return albumsEntity;
    }

    public AlbumsEntity findByName(String name){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        AlbumsEntity albumsEntity = em.find(AlbumsEntity.class,name);
        em.getTransaction().commit();
        em.close();
        pu.emf.close();
        return albumsEntity;
    }

    public List<AlbumsEntity> findByArtist(int artistId){
        EntityManager em = pu.emf.createEntityManager();
        em.getTransaction().begin();
        List list = em.createNativeQuery("findArtistId").setParameter("artist_id",artistId).getResultList();
        em.getTransaction().commit();
        em.close();
        pu.emf.close();
        return list;
    }

}