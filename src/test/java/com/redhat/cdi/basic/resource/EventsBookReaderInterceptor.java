package com.redhat.cdi.basic.resource;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import java.io.IOException;
import java.util.logging.Logger;

@Provider
public class EventsBookReaderInterceptor implements ReaderInterceptor {
    @Inject
    @EventsReadIntercept
    Event<String> readInterceptEvent;

    @Inject
    private Logger log;

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
        log.info("*** Intercepting call in EventsBookReaderInterceptor.read()");
        log.info("EventsBookReaderInterceptor firing readInterceptEvent");
        readInterceptEvent.fire("readInterceptEvent");
        Object result = context.proceed();
        log.info("*** Back from intercepting call in EventsBookReaderInterceptor.read()");
        return result;
    }

}

