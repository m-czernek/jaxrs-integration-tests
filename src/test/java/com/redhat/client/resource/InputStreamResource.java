package com.redhat.client.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class InputStreamResource {
   @Path("test")
   @Produces("text/plain")
   @GET
   public String get() {
      return "hello world";
   }
}
