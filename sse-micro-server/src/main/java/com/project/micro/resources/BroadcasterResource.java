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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;


@Singleton
@Path("/broadcast")
public class BroadcasterResource {

    private Sse sse;
    private SseBroadcaster broadcaster;

    public BroadcasterResource(@Context final Sse sse) {
        this.sse = sse;
        this.broadcaster = sse.newBroadcaster();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String broadcastMessage(String message) {
        final OutboundSseEvent event = sse.newEventBuilder()
                .name("message")
                .mediaType(MediaType.TEXT_PLAIN_TYPE)
                .data(String.class, message)
                .build();

        broadcaster.broadcast(event);
        
      
        System.out.println("Received: "+ message);
        

        return "Message '" + message + "' has been broadcast.";
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void listenToBroadcast(@Context SseEventSink eventSink) {
         System.out.println("Broadcast START " );
        this.broadcaster.register(eventSink);
    }

}
