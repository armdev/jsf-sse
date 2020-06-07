package com.project.chat;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.Data;
import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;


@ViewScoped
@Data
@Named
public class MessageListener implements Serializable {

    //private static final String BASE_URI = "http://localhost:9090";
    private static final long serialVersionUID = 1974510449199395815L;

    @Inject
    private MessageHolder messageHolder;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
//        scheduler = Executors.newSingleThreadScheduledExecutor();
//        scheduler.scheduleAtFixedRate(new MessageListener.HealthCheck(), 0, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        //  scheduler.shutdownNow();
    }
//
//    class HealthCheck implements Runnable {
//
//        @Override
//        public void run() {
//            checkSSE();
//        }
//
//    }

    public MessageListener() {

    }

    public void checkSSE() {
        System.out.println("Client start check");

        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        System.out.println("Client start client build");

        WebTarget target = client.target("http://localhost:9090/broadcast");
        System.out.println("Client target");

        EventInput eventInput = target.request().get(EventInput.class);

        System.out.println("eventInput " + eventInput.toString());
        
        while (!eventInput.isClosed()) {
            System.out.println("Event inout not closed");
            final InboundEvent inboundEvent = eventInput.read();
            
            if (inboundEvent == null) {
                // connection has been closed
                System.out.println("Connection closed");
                break;
            }
            final String responseMessage = inboundEvent.readData(String.class);

            System.out.println(inboundEvent.getName() + "; "
                    + inboundEvent.readData(String.class));
            System.out.println("ResponseMessage$ " + responseMessage);
            MessageDTO dto = new MessageDTO(responseMessage);
            System.out.println("Adding to List");
            messageHolder.getResponseMessagesList().add(0, dto);

        }

    }

    public void check() {

    }

}
