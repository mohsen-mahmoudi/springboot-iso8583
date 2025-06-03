package org.demo.iso.client.config;

import org.jpos.iso.ISOMsg;
import org.jpos.q2.iso.QMUX;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ISOClient {

    private final QMUX mux;

    public ISOClient(QMUX mux) {
        this.mux = mux;
    }

    public ISOMsg sendRequest(ISOMsg request, long timeout) throws Exception {
        request.set(11, "123456");
        //request.set(37, "123456654321");
        request.set(7, new SimpleDateFormat("MMddHHmmss").format(new Date()));
        return mux.request(request, timeout);
    }
}
