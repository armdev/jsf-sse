package com.project.chat;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author armdev
 */
@Named
@AllArgsConstructor
@SessionScoped
@Data
public class MessagePoster implements Serializable {

    private static final String BASE_URI = "http://localhost:9090";

    private String secretUsername;

    private String secretMessage;

    public MessagePoster() {

    }

    public void sendMessage() {
        System.out.println("SENDING MESSAGE");
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI + "/broadcast");
        String message = "@" + secretUsername + ": " + secretMessage;
        try {
            webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).post(javax.ws.rs.client.Entity.entity(message, javax.ws.rs.core.MediaType.TEXT_PLAIN));
            secretMessage = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        // messageListener.check();

    }

}
