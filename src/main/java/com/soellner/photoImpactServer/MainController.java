package com.soellner.photoImpactServer;


import com.soellner.photoImpactServer.data.Location;
import com.soellner.photoImpactServer.data.Photo;
import com.soellner.photoImpactServer.data.User;
import org.json.JSONException;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by asoel on 07.06.2016.
 */

//@WebServlet(name = "com.soellner.photoImpactServer.Greeting", urlPatterns = "/greeting")
@Path("/main")
public class MainController {

    Logger logger = Logger.getLogger(getClass().getName());



    private static String PERSISTENCE_UNIT = "soellnerMySQL";


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/checkLogin")
    public String postMessage(InputStream incomingData) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
        String line = null;
        StringBuilder userBuilder = new StringBuilder();
        while ((line = in.readLine()) != null) {
            userBuilder.append(line);
        }

        JSONObject jsonObject = new JSONObject(userBuilder.toString());

        String username = (String) jsonObject.get("username");
        String password = (String) jsonObject.get("password");

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        EntityManager manager = factory.createEntityManager();


        List<User> users = manager.createQuery("Select a From User a where a.login=?1 AND a.pass=?2", User.class).setParameter(1, username).setParameter(2, password).getResultList();
        if (!users.isEmpty()) {
            return "ok";

        }


        return "not authorized";
    }




    @POST
    @Path("/saveLocation")

    public void saveLocation(InputStream incomingData) {
        StringBuilder locationsBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                locationsBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(locationsBuilder.toString());

            //int userID = (Integer) jsonObject.get("userID");
            double latidue = jsonObject.getDouble("latitude");
            double longitude = jsonObject.getDouble("longitude");
            String username = jsonObject.getString("username");
            String password = jsonObject.getString("password");

            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            EntityManager manager = factory.createEntityManager();



            List<User> users = manager.createQuery("Select a From User a where a.login=?1 AND a.pass=?2", User.class).setParameter(1, username).setParameter(2, password).getResultList();
            if (users.isEmpty()) {
                return;
            }


            EntityTransaction tx = manager.getTransaction();
            tx.begin();

            Location location = new Location();
            //SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
            Date dt = new Date();
            //String readableDate = sdf.format(dt);
            location.setDateTime(dt.getTime()+"");
            location.setLatitude(latidue);
            location.setLongitude(longitude);
            location.setUserID(users.get(0).getId());

            manager.persist(location);

            tx.commit();


        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }


        // System.out.println("Data Received: " + locationsBuilder.toString());

        // return HTTP response 200 in case of success
        //return Response.status(200).entity();
    }


    @POST
    @Path("/uploadImage")

    public void crunchifyREST(InputStream incomingData) {
        StringBuilder crunchifyBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(crunchifyBuilder.toString());
            String imageAsBase64 = (String) jsonObject.get("image");


            BufferedImage image = null;
            byte[] imageBytes;
            BASE64Decoder decoder = new BASE64Decoder();
            imageBytes = decoder.decodeBuffer(imageAsBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);


            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            EntityManager manager = factory.createEntityManager();


            EntityTransaction tx = manager.getTransaction();
            tx.begin();

            Photo photo = new Photo();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS");
            Date dt = new Date();
            String readableDate = sdf.format(dt);
            photo.setDate(readableDate);
            photo.setImage(imageBytes);
            String dateTime = ((String) jsonObject.get("TAG_DATETIME")).trim();

            if (!jsonObject.get("TAG_GPS_LATITUDE").toString().equals("")) {
                Double latidue = (Double) jsonObject.get("TAG_GPS_LATITUDE");
                photo.setGpsLatidude(latidue);
            }

            if (!jsonObject.get("TAG_GPS_LONGITUDE").toString().equals("")) {
                Double longitude = (Double) jsonObject.get("TAG_GPS_LONGITUDE");
                photo.setGpsLongitude(longitude);
            }


            if (!dateTime.equals("") && !dateTime.equals("null")) {
                photo.setOriginalDateTime(dateTime);
            }

            manager.persist(photo);

            tx.commit();
            bis.close();



        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }

    }



    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on " + new Date().toString();
    }



}