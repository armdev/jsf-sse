package com.project.chat;

import java.io.Serializable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
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

        Client client = ClientBuilder.newBuilder().build();
        WebTarget target = client.target("http://localhost:9090/broadcast");
        //SseEventSource sseEventSource = SseEventSource.target(target).build();
        MessageDTO msgs = new MessageDTO();
        try (SseEventSource eventSource = SseEventSource.target(target).build()) {
            eventSource.register(new Consumer<InboundSseEvent>() {
                @Override
                public void accept(InboundSseEvent e) {
                    System.out.println("New event. this not work yet..");

                    msgs.setMessage(e.readData());
                }
            }, System.out::println);
            eventSource.open();
        }

        System.out.println("Client start check");

        messageHolder.getResponseMessagesList().add(0, msgs);

    }

}
