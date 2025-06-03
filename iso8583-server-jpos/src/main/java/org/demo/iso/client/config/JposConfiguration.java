package org.demo.iso.client.config;

import org.jpos.iso.ServerChannel;
import org.jpos.iso.channel.HEXChannel;
import org.jpos.iso.packager.PostPackager;
import org.jpos.q2.Q2;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class JposConfiguration {

    @Bean
    public Q2 q2() {
        Q2 q2 = new Q2();
        q2.start();
        return q2;
    }

    @Bean
    public Logger jposLogger() {
        Logger logger = new Logger();
        logger.addListener(new SimpleLogListener(System.out));
        return logger;
    }

    @Bean
    public PostPackager packager() {
        return new PostPackager();
    }

    @Bean
    public ServerChannel serverChannel() {
        ServerChannel channel = null;
        try {
            channel = new HEXChannel(packager(), new byte[]{});
            channel.setUsable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ((LogSource) channel).setLogger(jposLogger(), "hex-channel");
        return channel;
    }
}
