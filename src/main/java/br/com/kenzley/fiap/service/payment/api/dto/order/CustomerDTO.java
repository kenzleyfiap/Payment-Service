package br.com.kenzley.fiap.service.payment.api.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class CustomerDTO {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String cpf;
}
