package org.demo.iso.client.qclient;

import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class ClientTransactionHandler implements TransactionParticipant {
    private static final Logger logger = LoggerFactory.getLogger(ClientTransactionHandler.class);

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        try {
            logger.info("Preparing client transaction");
            // processing transaction
            return PREPARED;
        } catch (Exception e) {
            logger.error("Error in transaction", e);
            return ABORTED;
        }
    }

    @Override
    public void commit(long id, Serializable context) {
        logger.info("Transaction committed");
    }

    @Override
    public void abort(long id, Serializable context) {
        logger.warn("Transaction aborted");
    }
}
