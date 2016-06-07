package com.soellner.photoImpactServer;

import com.sun.jersey.core.header.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;
import sun.misc.IOUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipException;

/**
 * Created by asoel on 06.06.2016.
 */
@Path("/files")
public class UploadFileService {



    @GET
    @Produces(MediaType.TEXT_XML)
    @Path("/test2")
    public String sayXMLHello2() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey2" + "</hello>";
    }

   /* @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void uploadFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) throws Exception {
        OutputStream os = null;
        try {
            File fileToUpload = new File("C:/Users/Public/Pictures/Desert1.jpg");
            os = new FileOutputStream(fileToUpload);
            byte[] b = new byte[2048];
            int length;
            while ((length = uploadedInputStream.read(b)) != -1) {
                os.write(b, 0, length);
            }
        } catch (IOException ex) {
            Logger.getLogger(com.soellner.photoImpactServer.UploadFileService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(com.soellner.photoImpactServer.UploadFileService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }*/
}