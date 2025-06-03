package org.demo.iso.client.service.aware;

import org.demo.iso.client.service.IsoService;
import org.jpos.transaction.Context;
import org.jpos.transaction.TransactionParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class SpringAwareTransactionHandler implements TransactionParticipant {

    @Autowired
    private IsoService isoService;

    @Override
    public int prepare(long id, Serializable context) {
        Context ctx = (Context) context;
        // Use Spring service here
        isoService.processTransaction(ctx);
        return PREPARED;
    }

}
