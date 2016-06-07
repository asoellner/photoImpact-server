package com.soellner.photoImpactServer;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by asoel on 07.06.2016.
 */

//@WebServlet(name = "com.soellner.photoImpactServer.Greeting", urlPatterns = "/greeting")
@Path("/greeting")
public class GreetingController {



    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on "+new Date().toString();
    }



    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void  uploadImage1(@FormParam(value = "image") String image) {
        InputStream is = new ByteArrayInputStream(image.getBytes());
        //.....(MySQL Code to insert as BLOB)}
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
