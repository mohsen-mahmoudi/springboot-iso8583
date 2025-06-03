package org.demo.iso.client;

import org.demo.iso.client.listener.RequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ServerChannel;
import org.jpos.util.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Iso8583ServerApplication {

    @Value("${iso.server.port}")
    private int port;

    public static void main(String[] args) {
        SpringApplication.run(Iso8583ServerApplication.class, args);
        System.out.println("ISO8583 Server with Q2 started successfully");
    }

    @Bean
    public CommandLineRunner demo(ServerChannel serverChannel,
                                  Logger jposLogger,
                                  RequestListener requestListener) {
        return args -> {
            try {
                ISOServer server = new ISOServer(port, serverChannel, null);
                server.setLogger(jposLogger, "server");
                server.addISORequestListener(requestListener);
                server.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
