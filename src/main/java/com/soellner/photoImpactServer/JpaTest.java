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

public class JpaTest {

    public JpaTest(EntityManager manager) {

    }

    /**
     * @param args
     */

    public static void main(String[] args) {


        EntityManagerFactory factory = Persistence.createEntityManagerFactory("photoPersistence");
        EntityManager manager = factory.createEntityManager();
        JpaTest test = new JpaTest(manager);

        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            test.createPhotos(manager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tx.commit();

        test.listPhotos(manager);

        System.out.println(".. done");


    }

    private void createPhotos(EntityManager manager) {
        int numOfEmployees = manager.createQuery("Select a From Photo a", Photo.class).getResultList().size();
        //if (numOfEmployees == 0) {
        Photo photo = new Photo();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
        Date dt = new Date();
        String readableDate = sdf.format(dt);
        photo.setDate(readableDate);

        manager.persist(photo);

        //}
    }

    private void listPhotos(EntityManager manager) {
        List<Photo> resultList = manager.createQuery("Select a From Photo a", Photo.class).getResultList();
        System.out.println("num of employess:" + resultList.size());
        for (Photo next : resultList) {
            System.out.println("next Photo: " + next);
        }
    }


}
