package com.soellner.photoImpactServer;

import com.soellner.photoImpactServer.data.Photo;
import com.soellner.photoImpactServer.data.User;

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
    private static String PERSISTENCE_UNIT = "soellnerMySQL";
    public JpaTest(EntityManager manager) {

    }

    /**
     * @param args
     */

    public static void main(String[] args) {



        createDefaultUser();




    }

    private static void createDefaultUser() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager manager = factory.createEntityManager();
        int numOfEmployees = manager.createQuery("Select a From User a", User.class).getResultList().size();
        //if (numOfEmployees == 0) {
        if (numOfEmployees == 0) {
            EntityTransaction tx = manager.getTransaction();
            tx.begin();
            User user = new User();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
            Date dt = new Date();
            String readableDate = sdf.format(dt);
            user.setCreationDate(readableDate);
            user.setLogin("alex");
            user.setPass("password");
            manager.persist(user);
            System.out.println("created user: " + user);
            tx.commit();
        }

    }




    }
