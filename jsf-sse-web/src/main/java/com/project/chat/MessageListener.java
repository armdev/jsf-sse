package com.project.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ApplicationScoped;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.media.sse.EventListener;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

@ManagedBean(eager = true)
@AllArgsConstructor
@ApplicationScoped
public class MessageListener implements Serializable {

    private static final String BASE_URI = "http://localhost:9090";
    private static final long serialVersionUID = 1974510449199395815L;

    @ManagedProperty(value = "#{messageHolder}")
    @Setter
    private MessageHolder messageHolder;
    private ScheduledExecutorService scheduler;

    @PostConstruct
    public void init() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new MessageListener.HealthCheck(), 0, 5, TimeUnit.SECONDS);
    }

    @PreDestroy
    public void destroy() {
        scheduler.shutdownNow();
    }

    class HealthCheck implements Runnable {

        @Override
        public void run() {
            checkSSE();
        }

    }

    public MessageListener() {

    }

    public void checkSSE() {
        System.out.println("init called");
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget webTarget = client.target("http://localhost:9090/broadcast");
        EventSource eventSource = EventSource.target(webTarget).build();
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(InboundEvent inboundEvent) {
                if (inboundEvent.readData(String.class) != null) {
                    final String responseMessage = inboundEvent.readData(String.class);
                    //System.out.println(inboundEvent.getName() + "; "
                    //      + inboundEvent.readData(String.class));
                    System.out.println("responseMessage$ " + responseMessage);
                    MessageDTO dto = new MessageDTO(responseMessage);
                    messageHolder.getResponseMessagesList().add(0, dto);
                }

            }
        };

        //   System.out.println("responseMessage#### " + responseMessage);
        eventSource.register(listener, "message");
        eventSource.open();
        eventSource.close();
    }

    public void check() {

    }

}
