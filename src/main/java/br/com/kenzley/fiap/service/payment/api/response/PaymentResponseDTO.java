package br.com.kenzley.fiap.service.payment.api.response;

import com.mercadopago.client.preference.PreferenceItemRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class PaymentResponseDTO {
    private List<PreferenceItemRequest> items;
    private String initPoint;

}
