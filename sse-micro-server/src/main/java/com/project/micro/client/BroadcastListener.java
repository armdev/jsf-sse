package com.project.micro.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

public class BroadcastListener {

    public BroadcastListener() {

    }
    //https://jersey.java.net/documentation/latest/sse.html

    public void chatListener() {
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget target = client.target("http://localhost:9090/broadcast");

        EventInput eventInput = target.request().get(EventInput.class);
        while (!eventInput.isClosed()) {
            final InboundEvent inboundEvent = eventInput.read();
            if (inboundEvent == null) {
                // connection has been closed
                break;
            }
            System.out.println("received: " + inboundEvent.getName() + "; "
                    + inboundEvent.readData(String.class));
        }

        //eventSource.close();
    }

    public static void main(String[] args) {
        BroadcastListener wc = new BroadcastListener();
        wc.chatListener();

    }
}
