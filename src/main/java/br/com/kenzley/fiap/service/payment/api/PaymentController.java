package br.com.kenzley.fiap.service.payment.api;

import br.com.kenzley.fiap.service.payment.api.dto.order.OrderDTO;
import br.com.kenzley.fiap.service.payment.api.response.PaymentResponseDTO;
import br.com.kenzley.fiap.service.payment.business.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody OrderDTO orderDTO) {
        return paymentService.processPayment(orderDTO);
    }
}
