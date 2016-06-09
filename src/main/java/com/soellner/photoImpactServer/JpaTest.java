package com.soellner.photoImpactServer;

import com.soellner.photoImpactServer.data.Photo;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.persistence.*;
import javax.transaction.UserTransaction;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by asoel on 09.06.2016.
 */
@Stateless
public class JpaTest {

    @PersistenceContext(unitName = "photoPersistence")
    private EntityManager _entityManager;


    public JpaTest() {

    }

    /**
     * @param args
     */

    public static void main(String[] args) {


        //EntityManager factory = Persistence.createEntityManagerFactory("photoPersistence");
        //EntityManager manager = factory.createEntityManager();
        JpaTest test = new JpaTest();

        //EntityTransaction tx = _entityManager.getTransaction();

        test.createPhotos();


    }

    private void createPhotos() {
        EntityTransaction tx = _entityManager.getTransaction();
        tx.begin();

        int numOfEmployees = _entityManager.createQuery("Select a From Photo a", Photo.class).getResultList().size();
        if (numOfEmployees == 0) {
            Photo photo = new Photo();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
            Date dt = new Date();
            String readableDate = sdf.format(dt);
            photo.setDate(readableDate);

            _entityManager.persist(photo);
            tx.commit();

        }
    }

    private void listPhotos() {
        List<Photo> resultList = _entityManager.createQuery("Select a From Photo a", Photo.class).getResultList();
        System.out.println("num of employess:" + resultList.size());
        for (Photo next : resultList) {
            System.out.println("next Photo: " + next);
        }
    }


}
