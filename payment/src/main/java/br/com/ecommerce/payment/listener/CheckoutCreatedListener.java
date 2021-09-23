package br.com.ecommerce.payment.listener;

import br.com.ecommerce.checkout.event.CheckoutCreatedEvent;
import br.com.ecommerce.payment.entity.PaymentEntity;
import br.com.ecommerce.payment.event.PaymentCreatedEvent;
import br.com.ecommerce.payment.service.PaymentService;
import br.com.ecommerce.payment.streaming.CheckoutProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckoutCreatedListener {

    private final CheckoutProcessor checkoutProcessor;

    private final PaymentService paymentService;

    @StreamListener(CheckoutProcessor.INPUT)
    public void handler(CheckoutCreatedEvent checkoutCreatedEvent){

        log.info("checkoutCreatedEvent={}", checkoutCreatedEvent);

        final PaymentEntity paymentEntity = paymentService.create(checkoutCreatedEvent).orElseThrow();

        final PaymentCreatedEvent paymentCreatedEvent = PaymentCreatedEvent.newBuilder()
                    .setCheckoutCode(paymentEntity.getCheckoutCode())
                    .setPaymentCode(paymentEntity.getCode())
                    .build();

        checkoutProcessor.output().send(MessageBuilder.withPayload(paymentCreatedEvent).build());
    }

}
