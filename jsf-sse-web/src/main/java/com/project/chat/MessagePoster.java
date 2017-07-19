package com.project.chat;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author armdev
 */
@ManagedBean
@AllArgsConstructor
@SessionScoped
public class MessagePoster {

    private static final String BASE_URI = "http://localhost:9090";

    @Setter
    @Getter
    private String secretUsername;

    @Setter
    @Getter
    private String secretMessage;


    public MessagePoster() {

    }

    public void sendMessage() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(BASE_URI + "/broadcast");
        String message = "@" + secretUsername + ": " + secretMessage;
        try{
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).post(javax.ws.rs.client.Entity.entity(message, javax.ws.rs.core.MediaType.TEXT_PLAIN)); 
        secretMessage = null;
        }catch(Exception e){
            e.printStackTrace();
        }
       // messageListener.check();

    }

}
