package org.demo.iso.client.qclient;

import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOSource;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyISORequestListener implements ISORequestListener {
    private static final Logger logger = LoggerFactory.getLogger(MyISORequestListener.class);

    private final TransactionManager txMgr;

    public MyISORequestListener(TransactionManager txMgr) {
        this.txMgr = txMgr;
    }

    @Override
    public boolean process(ISOSource source, ISOMsg m) {
        try {
            Context ctx = new Context();
            ctx.put("SOURCE", source);
            ctx.put("REQUEST", m);

            txMgr.push(ctx);
            return true;
        } catch (Exception e) {
            logger.error("Error processing request", e);
            sendErrorResponse(source, m);
            return false;
        }
    }

    private void sendErrorResponse(ISOSource source, ISOMsg m) {
        try {
            ISOMsg resp = (ISOMsg) m.clone();
            resp.setResponseMTI();
            resp.set(39, "99"); // System error
            source.send(resp);
        } catch (Exception e) {
            logger.error("Failed to send error response", e);
        }
    }
}
