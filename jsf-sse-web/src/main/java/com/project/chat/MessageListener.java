package com.project.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
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

@ManagedBean
@AllArgsConstructor
@SessionScoped
public class MessageListener implements Serializable {

    private static final String BASE_URI = "http://localhost:9090";
    private static final long serialVersionUID = 1974510449199395815L;

    @Setter
    @Getter
    private String responseMessage;
    
    @ManagedProperty(value = "#{messageHolder}")
    @Setter
    private MessageHolder messageHolder;

    public MessageListener() {

    }

    @PostConstruct
    public void init() {
        System.out.println("init called");
        Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
        WebTarget webTarget = client.target("http://localhost:9090/broadcast");
        EventSource eventSource = EventSource.target(webTarget).build();
        EventListener listener = new EventListener() {
            @Override
            public void onEvent(InboundEvent inboundEvent) {
                responseMessage = inboundEvent.readData(String.class);
                System.out.println(inboundEvent.getName() + "; "
                        + inboundEvent.readData(String.class));
                MessageDTO dto = new MessageDTO(responseMessage);
                messageHolder.getResponseMessagesList().add(0, dto);
            }
        };
        eventSource.register(listener, "message");
        eventSource.open();
        eventSource.close();
    }

}
