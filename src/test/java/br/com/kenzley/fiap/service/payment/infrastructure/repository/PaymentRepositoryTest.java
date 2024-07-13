package br.com.kenzley.fiap.service.payment.infrastructure.repository;

import br.com.kenzley.fiap.service.payment.infrastructure.entity.PaymentEntity;
import br.com.kenzley.fiap.service.payment.infrastructure.utils.PaymentHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PaymentRepositoryTest {

    @Mock
    private PaymentRepository paymentRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void mustAllowCreatePayment() {
        // Arrange
        var payment = PaymentHelper.gerarPaymentEntity();
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(payment);

        // Act
        var paymentRegistred = paymentRepository.save(payment);

        // Assert

        assertThat(paymentRegistred)
                .isNotNull()
                .isEqualTo(payment);

        verify(paymentRepository, times(1)).save(any(PaymentEntity.class));
    }

}