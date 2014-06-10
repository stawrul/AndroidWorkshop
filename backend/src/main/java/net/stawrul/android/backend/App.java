package net.stawrul.android.backend;

import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class App {
    public final static String BASE_URI = "http://0.0.0.0:9999/";

    public static void main(String[] args) throws IOException, URISyntaxException {
        System.out.println("Starting server...");
        ResourceConfig resourceConfig = new ResourceConfig(BackendResource.class);
        final HttpServer server = JdkHttpServerFactory.createHttpServer(new URI(BASE_URI),
                resourceConfig);


        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Stopping server...");
                server.stop(0);
            }
        });

    }
}
