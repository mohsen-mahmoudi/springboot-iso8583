package org.demo.iso.client.qserver;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class CustomTransactionHandler implements TransactionParticipant {
    private static final Logger logger = LoggerFactory.getLogger(CustomTransactionHandler.class);

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOMsg req = (ISOMsg) ctx.get("REQUEST");

        try {
            logger.info("Processing transaction with PAN: {}", req.getString(2));

            // Process transaction logic here
            ISOMsg resp = (ISOMsg) req.clone();
            resp.setResponseMTI();
            resp.set(39, "00"); // Approval code

            ctx.put("RESPONSE", resp);
            return PREPARED;
        } catch (Exception e) {
            logger.error("Transaction error", e);
            return ABORTED;
        }
    }

    @Override
    public void commit(long id, Serializable context) {
        // Additional commit logic if needed
    }

    @Override
    public void abort(long id, Serializable context) {
        Context ctx = (Context) context;
        ISOMsg req = (ISOMsg) ctx.get("REQUEST");
        ISOMsg resp = (ISOMsg) req.clone();
        try {
            resp.setResponseMTI();
        } catch (ISOException e) {
            throw new RuntimeException(e);
        }
        resp.set(39, "06"); // Error code

        ctx.put("RESPONSE", resp);
    }
}
