package org.demo.iso.client.service;

import org.jpos.transaction.Context;
import org.springframework.stereotype.Service;

@Service
public class IsoService {
    public void processTransaction(Context ctx) {
        // Business logic using Spring features
        System.out.printf("context: %s\n", ctx);
    }
}
