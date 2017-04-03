/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.micro.resources;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.glassfish.jersey.media.sse.SseFeature;

@Singleton
@Path("/broadcast")
public class BroadcasterResource {

    //https://jersey.java.net/documentation/latest/sse.html
    
    private final SseBroadcaster broadcaster = new SseBroadcaster();

    @POST    
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(String message) {
        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
        OutboundEvent event = eventBuilder.name("message")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, message)
                .build();
        broadcaster.broadcast(event);
        System.out.println(" Received::::: " + message );
        return "MessageKUKU '" + message + "' has been broadcast.";
    }

    @GET        
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput listenToBroadcast() {
        final EventOutput eventOutput = new EventOutput();
        System.out.println("###eventOutput " + eventOutput.toString() );
        this.broadcaster.add(eventOutput);
        return eventOutput;
    }

}
