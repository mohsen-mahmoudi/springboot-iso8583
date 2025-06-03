package org.demo.iso.client.config;

import org.jpos.q2.iso.QMUX;
import org.jpos.util.NameRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Q2ClientConfig {

    @Bean
    public QMUX exposeQmux() throws Exception {
        QMUX qmux = NameRegistrar.get("mux.customMux");
        qmux.start();
        qmux.startService();
        qmux.addISORequestListener((source, m) -> {
            System.out.println(source + ": " + m);
            return true;
        });
        return qmux;
    }
}
