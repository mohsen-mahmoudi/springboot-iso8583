package org.demo.iso.client;

import org.demo.iso.client.config.ISOClient;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.channel.HEXChannel;
import org.jpos.iso.packager.PostPackager;
import org.jpos.q2.Q2;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@SpringBootApplication
public class Iso8583ClientApplication {

    public static void main(String[] args) {
        Q2 q2 = new Q2("C:\\_sources\\work-personal\\iso8583-server\\iso8583-client-jpos\\deploy");
        q2.start();
        SpringApplication.run(Iso8583ClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ISOClient isoClient) {
        return args -> {
            while (true) {
                Logger logger = new Logger();
                logger.addListener(new SimpleLogListener(System.out));
                PostPackager p = new PostPackager();
                HEXChannel channel = new HEXChannel(
                        "localhost", 8000, p, new byte[]{}
                );
                channel.setLogger(logger, "test-channel");
                channel.connect();

                ISOMsg request = new ISOMsg();
                request.setPackager(p);
                request.setHeader(new byte[]{});
                request.setMTI("0200");
                request.set(2, "6219861012345678");
                request.set(3, "000000");
                request.set(4, "000000100000");
                request.set(11, "123456");
                request.set(27, "0");
                //request.set(37, "123456654321");
                request.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));

                System.out.println("Request Sended: " + Arrays.toString(request.pack()));
                //ISOMsg response = isoClient.sendRequest(request, 30000);

                channel.send(request);
                ISOMsg response = channel.receive();
                channel.disconnect();

                System.out.println("Response: " + response);

                Thread.sleep(5000);
            }
        };
    }
}
