import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by asoel on 01.06.2016.
 */
@WebServlet(name = "hello", urlPatterns = "/")
public class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello Sepp");
    }


    @POST
    @Path("testUpload")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    public String uploadStream( InputStream payload ) throws IOException
    {
        while(true) {
            try {
                DataInputStream dis = new DataInputStream(payload);
                System.out.println(dis.readByte());
            } catch (Exception e) {
                break;
            }
        }
        //Or you can save the inputsream to a file directly, use the code, but must remove the while() above.
        /**
         OutputStream os =new FileOutputStream("C:\recieved.jpg");
         IOUtils.copy(payload,os);
         **/
        System.out.println("Payload size="+payload.available());
        return "Payload size="+payload.available();

    }

}
