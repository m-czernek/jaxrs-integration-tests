package com.redhat.resource.basic.resource;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.logging.LogManager;

@Path("/")
public class MultipleEndpointsWarningResource {
   
   @SuppressWarnings("unused")
   private static String MESSAGE_CODE = "RESTEASY002142";
   private LogHandler logHandler = new LogHandler();
   
   @Path("setup")
   @GET
   public void setup()
   {
      LogManager.getLogManager().getLogger("test").addHandler(logHandler);
   }
   
   @Path("teardown")
   @GET
   public void teardown()
   {

   }
   
   @Path("unique")
   @GET
   @Produces("text/plain")
   public int unique() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @GET
   @Produces("text/plain")
   public int getVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @POST
   @Produces("text/plain")
   public int postVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("verbs")
   @PUT
   @Produces("text/plain")
   public int putVerb() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("duplicate")
   @GET
   public int duplicate1() throws Exception {
      return logHandler.getMessagesLogged();
   }

   @Path("duplicate")
   @GET
   public int duplicate2() throws Exception {
      return logHandler.getMessagesLogged();
   }
   
   @GET
   @Path("{id}")
   public int withId(@PathParam("id") Integer id) {
      return logHandler.getMessagesLogged();
   }
}