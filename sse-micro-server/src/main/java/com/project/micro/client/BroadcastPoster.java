package com.project.micro.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class BroadcastPoster {

    private static final String BASE_URI = "http://localhost:9090";

    private final WebTarget webTarget;

    public BroadcastPoster() {
        Client client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI + "/broadcast");
    }

    public void post(String requestEntity) {        
        webTarget.request(javax.ws.rs.core.MediaType.TEXT_PLAIN).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.TEXT_PLAIN));
    }


    public static void main(String[] args) {
        BroadcastPoster wc = new BroadcastPoster();
        for (int i = 0; i < 10000; i++) {
            Long tt = System.currentTimeMillis();
            System.out.println("Passing " + tt);
            wc.post(tt.toString());
        }
     

    }
}
