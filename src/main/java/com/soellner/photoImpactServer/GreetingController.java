package com.soellner.photoImpactServer;


import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.util.Base64;
import org.json.JSONObject;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
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


    @POST
    @Path("/crunchifyService")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crunchifyREST(InputStream incomingData) {
        StringBuilder crunchifyBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while ((line = in.readLine()) != null) {
                crunchifyBuilder.append(line);
            }

            JSONObject jsonObject = new JSONObject(crunchifyBuilder.toString());
            String imageAsBase64=(String)jsonObject.get("image");
            //byte[] decodedBytes = Base64.decode(imageAsBase64);

            BufferedImage image = null;
            byte[] imageByte;
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageAsBase64);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();

            String outputpath = "C:/Users/alexa/Pictures/temp.jpg";

            File imageFile=new File(outputpath);
            ImageIO.write(image, "jpg", imageFile);


        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }



        System.out.println("Data Received: " + crunchifyBuilder.toString());

        // return HTTP response 200 in case of success
        return Response.status(200).entity(crunchifyBuilder.toString()).build();
    }


    @GET
    @Path("/verify")
    @Produces(MediaType.TEXT_PLAIN)
    public Response verifyRESTService(InputStream incomingData) {
        String result = "CrunchifyRESTService Successfully started..";

        // return HTTP response 200 in case of success
        return Response.status(200).entity(result).build();
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
