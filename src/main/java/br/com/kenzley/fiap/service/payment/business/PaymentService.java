package br.com.kenzley.fiap.service.payment.business;

import br.com.kenzley.fiap.service.payment.api.dto.order.OrderDTO;
import br.com.kenzley.fiap.service.payment.api.response.PaymentResponseDTO;
import br.com.kenzley.fiap.service.payment.infrastructure.entity.PaymentEntity;
import br.com.kenzley.fiap.service.payment.infrastructure.exceptions.MercadoPagoException;
import br.com.kenzley.fiap.service.payment.infrastructure.repository.PaymentRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${mercado.pago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${order.payment.status.url}")
    private String orderPaymentStatusUrl;

    private final PreferenceClient preferenceClient;

    private final PaymentRepository paymentRepository;

    public PaymentResponseDTO processPayment(OrderDTO orderDTO) {

        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            List<PreferenceItemRequest> items = new ArrayList<>();

            orderDTO.getProducts().forEach(product -> {

                PreferenceItemRequest itemRequest =
                        PreferenceItemRequest.builder()
                                .id(product.getProductId())
                                .title(product.getProductName())
                                .description(product.getProductDescription())
                                .categoryId(product.getCategoryProduct())
                                .quantity(product.getQuantityProduct())
                                .currencyId("BRL")
                                .unitPrice(product.getProductPrice())
                                .build();

                items.add(itemRequest);
            });

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .backUrls(
                            PreferenceBackUrlsRequest.builder()
                                    .success(orderPaymentStatusUrl + "/" + orderDTO.getIdOrder() + "/success")
                                    .failure(orderPaymentStatusUrl + "/" + orderDTO.getIdOrder() + "/failure")
                                    .pending(orderPaymentStatusUrl + "/" + orderDTO.getIdOrder() + "/pending")
                                    .build())
                    .expires(false)
                    .autoReturn("all")
                    .binaryMode(true)
                    .operationType("regular_payment")
                    .items(items)
                    .build();

            Preference preference = preferenceClient.create(preferenceRequest);


            var paymentEntiy = PaymentEntity.builder()
                            .orderId(orderDTO.getIdOrder())
                            .collectorId(preference.getCollectorId())
                            .clientId(preference.getClientId())
                            .dateCreated(preference.getDateCreated().toLocalDateTime())
                            .initPoint(preference.getInitPoint())
                            .build();

            paymentRepository.save(paymentEntiy);

            return PaymentResponseDTO.builder()
                    .items(items)
                    .initPoint(preference.getInitPoint())
                    .build();

        } catch (MPApiException apiException) {
            System.out.println(apiException.getApiResponse().getContent());
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            System.out.println(exception.getMessage());
            throw new MercadoPagoException(exception.getMessage());
        }
    }
}
