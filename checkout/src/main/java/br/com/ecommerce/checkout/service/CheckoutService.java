package br.com.ecommerce.checkout.service;

import br.com.ecommerce.checkout.entity.CheckoutEntity;
import br.com.ecommerce.checkout.resource.checkout.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);

}
