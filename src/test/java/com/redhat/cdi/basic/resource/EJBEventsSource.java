package com.redhat.cdi.basic.resource;

import javax.ejb.Local;
import javax.ws.rs.PathParam;

@Local
public interface EJBEventsSource {
    int createBook(EJBBook book);

    EJBBook lookupBookById(@PathParam("id") int id);

    boolean test();
}

