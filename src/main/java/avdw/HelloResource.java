package avdw;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import org.codehaus.jackson.map.ObjectMapper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World PLAIN text";
    }

    @GET
    @Produces(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?><hello> Hello World XML, YAY!!!</hello>";
    }
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html><title>Hello World HTML</title><body><h1>Hello World HTML JRebel Rules!</body></h1></html>";
    }

    @POST
    @Consumes({"text/xml", "text/plain", MediaType.TEXT_HTML})
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPostHello() {
        return "Hello World Post!";
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayPostJsonHello(String json) {
        Map rowMapObject = null;
        try {
            rowMapObject = new ObjectMapper().readValue(json, HashMap.class);
        } catch (IOException e) {
            return Response.status(400).entity("Bad json").build();
        }
        return Response.ok("Hello, parsed json to map: " + rowMapObject.toString()).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail) {

        String uploadedFileLocation = "/home/olgab/" + fileDetail.getFileName();

        // save it
        try {
            writeToFile(uploadedInputStream, uploadedFileLocation);
        }catch(Exception e)
        {
            return Response.status(500).entity(e).build();
        }
        String output = "File uploaded to : " + uploadedFileLocation;
        return Response.status(200).entity(output).build();

    }

    // save uploaded file to new location
    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) throws Exception {
            int read = 0;
            byte[] bytes = new byte[1024];

            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();

    }
}