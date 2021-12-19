package gr.codelearn.listener;

import gr.codelearn.gateway.PaymentsGateway;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@AllArgsConstructor
public class RabbitMQListener {

    private static final String queueName = "payment.queue";
    public static final String walletQueueName = "wallet.queue";
    public static final String WALLET_CHANNEL = "wallet";
    public static final String COMMON_CHANNEL = "normal";
    private PaymentsGateway paymentsGateway;

    @RabbitListener(queues = queueName)
    public void consumePayment(Map<String, Object> payload) {
        log.info("A payment payload has been received.");
        payload.put("channel", COMMON_CHANNEL );
        paymentsGateway.initiatePayment(payload);
    }

    @RabbitListener(queues = walletQueueName)
    public void consumeWalletPayment(Map<String, Object> payload) {
        log.info("A wallet payment payload has been received.");
        payload.put("channel",WALLET_CHANNEL);
        paymentsGateway.initiateWalletPayment(payload);
    }
}

