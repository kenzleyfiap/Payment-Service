package br.com.kenzley.fiap.service.payment.infrastructure.repository;

import br.com.kenzley.fiap.service.payment.infrastructure.entity.PaymentEntity;
import br.com.kenzley.fiap.service.payment.infrastructure.utils.PaymentHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@ActiveProfiles("test")
class PaymentRepositoryIT {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    void mustCreatePayment() {

        // Arrange
        var id = UUID.randomUUID().toString();
        var payment = PaymentHelper.gerarPaymentEntity();

        payment.setId(id);

        // Act
        var paymentReceived = paymentRepository.save(payment);

        // Assert
        assertThat(paymentReceived)
                .isInstanceOf(PaymentEntity.class)
                .isNotNull();

        assertThat(paymentReceived.getId()).isEqualTo(id);
        assertThat(paymentReceived.getClientId()).isEqualTo(payment.getClientId());
        assertThat(paymentReceived.getOrderId()).isEqualTo(payment.getOrderId());
        assertThat(paymentReceived.getInitPoint()).isEqualTo(payment.getInitPoint());

    }

}