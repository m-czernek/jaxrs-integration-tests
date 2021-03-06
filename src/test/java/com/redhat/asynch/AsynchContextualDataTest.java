package com.redhat.asynch;

import com.redhat.asynch.resource.AsynchContextualDataProduct;
import com.redhat.asynch.resource.AsynchContextualDataResource;
import com.redhat.utils.PortProviderUtil;
import com.redhat.utils.TestUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @tpSubChapter Asynchronous RESTEasy: RESTEASY-1225
 * @tpChapter Integration tests
 * @tpTestCaseDetails Tests that Providers context is not discarded prematurely
 * @tpSince RESTEasy 3.1.1.Final
 */
@RunWith(Arquillian.class)
@RunAsClient
public class AsynchContextualDataTest {

   public static Client client;
   
   @Deployment()
   public static Archive<?> deploy() {
      WebArchive war = TestUtil.prepareArchive(AsynchContextualDataTest.class.getSimpleName());
      war.addClass(AsynchContextualDataProduct.class);
      List<Class<?>> singletons = new ArrayList<Class<?>>();
      singletons.add(AsynchContextualDataResource.class);
      return TestUtil.finishContainerPrepare(war, null, singletons);
   }
   
   private String generateURL(String path) {
      return PortProviderUtil.generateURL(path, AsynchContextualDataTest.class.getSimpleName());
   }

   @BeforeClass
   public static void initClient() {
      client = ClientBuilder.newClient();
   }

   @AfterClass
   public static void closeClient() {
      client.close();
   }

    /**
     * @tpTestDetails Test stack handling of context data map
     * @tpSince RESTEasy 3.1.1.Final
     */
    @Test
    public void testContextualData() throws Exception {
       String id = "334";

       //Start the request to the waiting endpoint, but don't block
       WebTarget target = client.target(generateURL("/products/wait/" + id));
       Future<Response> response = target.request().async().get();

       //Let the server set the resumable field, timing thing!
       Thread.sleep(3000);

       //While the other request is waiting, fire off a request to /res/ which will allow the other request to complete
       WebTarget resTarget = client.target(generateURL("/products/res/" + id));
       Response resResponse = resTarget.request().get();
       
       String entity = response.get().readEntity(String.class);
       String resEntity = resResponse.readEntity(String.class);

       response.get().close();
       resResponse.close();
    }
}
