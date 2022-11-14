package OxPay.Payment.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface TransactionMonitorService {
    void webSocketPull() throws ExecutionException, InterruptedException, IOException;
}
