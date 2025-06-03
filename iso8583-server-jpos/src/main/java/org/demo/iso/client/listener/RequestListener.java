package org.demo.iso.client.listener;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.springframework.stereotype.Component;

@Component
public class RequestListener implements ISORequestListener {

    @Override
    public boolean process(ISOSource source, ISOMsg m) {
        try {
            ISOMsg response = (ISOMsg) m.clone();
            System.out.println("Received ISOMsg: " + response);
            response.setMTI("0210");
            //response.set(39,"00");
            source.send(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}