package br.com.kenzley.fiap.service.payment.infrastructure.utils;

import br.com.kenzley.fiap.service.payment.api.dto.order.CustomerDTO;
import br.com.kenzley.fiap.service.payment.api.dto.order.OrderDTO;
import br.com.kenzley.fiap.service.payment.api.dto.order.ProductDTO;
import br.com.kenzley.fiap.service.payment.api.response.PaymentResponseDTO;
import br.com.kenzley.fiap.service.payment.infrastructure.entity.PaymentEntity;
import com.mercadopago.client.preference.PreferenceItemRequest;

import java.math.BigDecimal;
import java.util.List;

public class PaymentHelper {

    public static PaymentEntity gerarPaymentEntity() {
        return PaymentEntity.builder()
                .orderId(1L)
                .collectorId(1L)
                .initPoint("teste")
                .clientId("clientId")
                .productIds(List.of("productId", "productId"))
                .build();
    }

    public static OrderDTO gerarOrderDTO() {
        return OrderDTO.builder()
                .idOrder(1L)
                .customer(new CustomerDTO("teste", "teste@mail.com", "1234567809"))
                .products(List.of(gerarProductDTO()))
                .build();
    }

    public static ProductDTO gerarProductDTO() {
        return ProductDTO.builder()
                .categoryProduct("category")
                .productDescription("description")
                .productId("idProduct")
                .productName("nameProduct")
                .productPrice(new BigDecimal("1.0"))
                .build();
    }

    public static PaymentResponseDTO gerarPaymentResponse() {
        return PaymentResponseDTO.builder()
                .items(List.of(PreferenceItemRequest.builder().build()))
                .initPoint("teste")
                .build();
    }
}
