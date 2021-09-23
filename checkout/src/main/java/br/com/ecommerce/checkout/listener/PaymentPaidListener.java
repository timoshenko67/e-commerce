package br.com.ecommerce.checkout.listener;

import br.com.ecommerce.checkout.entity.CheckoutEntity;
import br.com.ecommerce.checkout.service.CheckoutService;
import br.com.ecommerce.checkout.streaming.PaymentPaidSink;
import br.com.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    final CheckoutService checkoutService;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event){

        checkoutService.updateStatus(event.getCheckoutCode().toString(), CheckoutEntity.Status.APPROVED);

    }

}
