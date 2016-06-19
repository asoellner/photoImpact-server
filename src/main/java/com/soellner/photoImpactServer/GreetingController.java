package com.soellner.photoImpactServer;


import com.soellner.photoImpactServer.data.Location;
import com.soellner.photoImpactServer.data.Photo;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.Base64;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.util.logging.resources.logging;

import javax.imageio.ImageIO;
import javax.imageio.spi.ServiceRegistry;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by asoel on 07.06.2016.
 */

//@WebServlet(name = "com.soellner.photoImpactServer.Greeting", urlPatterns = "/greeting")
@Path("/greeting")
public class GreetingController {

    Logger logger = Logger.getLogger(getClass().getName());


    private static String PERSISTENCE_PHOTO_UNIT = "photosMySQL";
    private static String PERSISTENCE_LOCATIONS_UNIT = "locationsMySQL";
    //private static String PERSISTENCE_UNIT="photoPersistence_work";



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

            int userID=(int)jsonObject.get("userID");
            double latidue=jsonObject.getDouble("latitude");
            double longitude=jsonObject.getDouble("longitude");

            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_LOCATIONS_UNIT);
            EntityManager manager = factory.createEntityManager();

            EntityTransaction tx = manager.getTransaction();
            tx.begin();

            Location location = new Location();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
            Date dt = new Date();
            String readableDate = sdf.format(dt);
            location.setDateTime(readableDate);
            location.setLatitude(latidue);
            location.setLongitude(longitude);
            location.setUserID(userID);

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
    @Path("/crunchifyService")

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
            //byte[] decodedBytes = Base64.decode(imageAsBase64);

            BufferedImage image = null;
            byte[] imageBytes;
            BASE64Decoder decoder = new BASE64Decoder();
            imageBytes = decoder.decodeBuffer(imageAsBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
            //image = ImageIO.read(bis);



/*

            String outputpath = "C:/Users/alexa/Pictures/temp.jpg";
            File imageFile = new File(outputpath);
            if (imageFile.exists()) {
                imageFile.delete();
            }


            ImageIO.write(image, "jpg", imageFile);
*/


            EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_PHOTO_UNIT);
            EntityManager manager = factory.createEntityManager();


            //List<Photo> resultList = manager.createQuery("Select a From Photo a", Photo.class).getResultList();
            //System.out.println("num of photos:" + resultList.size());

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


            String ss = humanReadableByteCount(imageBytes.length, true);

            logger.log(Level.ALL, ss);

            System.err.println(ss);

        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }


        // System.out.println("Data Received: " + crunchifyBuilder.toString());

        // return HTTP response 200 in case of success
        //return Response.status(200).entity();
    }


    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }


    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on " + new Date().toString();
    }

    @POST
    @Path("sepp")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

    public void execute(@FormParam(value = "image") String image) {
        System.out.println(image);
    }


    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("text/plain")
    public String uploadImage1(@FormParam(value = "image") String image) {
        InputStream is = new ByteArrayInputStream(image.getBytes());
        return "received ping on " + new Date().toString();
    }


    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/test")
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("/test2")
    public String sayXMLHello2() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey2" + "</hello>";
    }


    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello Sepp1111");
    }
*/
}
