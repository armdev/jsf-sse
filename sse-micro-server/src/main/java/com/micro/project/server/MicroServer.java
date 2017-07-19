package com.micro.project.server;

import com.project.micro.resources.BroadcasterResource;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.*;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.*;


public class MicroServer {

    private static final String BASE_URL = "http://localhost:9090/";

    public static void main(String[] args) {
        try {
            System.out.println("Starting server: " + BASE_URL);

            final ResourceConfig resourceConfig = new ResourceConfig();                        
            resourceConfig.register(BroadcasterResource.class);            
            HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URL), resourceConfig, false);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                server.shutdownNow();
            }));
            
            HttpServerProbe probe = new HttpServerProbe.Adapter() {
                @Override
                public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
                    System.out.println("onRequestReceiveEvent " + request.getRequestURI());
                }
            };
            server.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(probe);
            server.start();
            System.out.println(format("Server started.%n => url=%s%n", BASE_URL));            
            Thread.currentThread().join();
            server.shutdown();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MicroServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
