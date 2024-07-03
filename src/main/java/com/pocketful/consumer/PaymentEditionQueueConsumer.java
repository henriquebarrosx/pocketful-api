package com.pocketful.consumer;

import com.pocketful.service.PaymentService;
import com.pocketful.web.dto.payment.PaymentEditionQueuePayload;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Slf4j
@AllArgsConstructor
@Component
public class PaymentEditionQueueConsumer {
    private final PaymentService paymentService;

    @RabbitListener(queues = {"${queue.payments_edition_queue}"})
    public void receive(@Payload PaymentEditionQueuePayload payload) {
        log.info("PaymentEditionQueueConsumer.receive start payload: {}", payload);
        paymentService.processPaymentEdition(payload.getPayment(), payload.getType());
        log.info("PaymentEditionQueueConsumer.receive end payload: {}", payload);
    }
}
