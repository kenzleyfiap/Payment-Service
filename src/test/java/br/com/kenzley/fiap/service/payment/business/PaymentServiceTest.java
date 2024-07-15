package br.com.kenzley.fiap.service.payment.business;

import br.com.kenzley.fiap.service.payment.api.dto.order.OrderDTO;
import br.com.kenzley.fiap.service.payment.api.response.PaymentResponseDTO;
import br.com.kenzley.fiap.service.payment.infrastructure.repository.PaymentRepository;
import br.com.kenzley.fiap.service.payment.utils.PaymentHelper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.yaml")
class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Value("${mercado.pago.access.token}")
    private String mercadoPagoAccessToken;

    @Value("${order.payment.status.url}")
    private String orderPaymentStatusUrl;
    private PaymentService paymentService;
    @Mock
    private PreferenceClient preferenceClient;
    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        paymentService = new PaymentService(
                preferenceClient,
                paymentRepository
        );
    }
    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    @Severity(SeverityLevel.BLOCKER)
    void mustAllowRegisterPayment() throws MPException, MPApiException {
        // Configurar o token de acesso MercadoPago
        MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

        // Arrange
        OrderDTO orderDTO = PaymentHelper.gerarOrderDTO();

        // Simular a resposta da preferência
        Preference preference = mock(Preference.class);

        when(preference.getDateCreated()).thenReturn(OffsetDateTime.now());
        when(preferenceClient.create(any(PreferenceRequest.class))).thenReturn(preference);

        // Act
        PaymentResponseDTO paymentRegistred = paymentService.processPayment(orderDTO);

        // Assert
        assertThat(paymentRegistred).isInstanceOf(PaymentResponseDTO.class).isNotNull();
    }
}