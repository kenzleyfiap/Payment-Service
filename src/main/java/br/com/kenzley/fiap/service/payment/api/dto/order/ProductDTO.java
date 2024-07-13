package br.com.kenzley.fiap.service.payment.api.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductDTO {

    @NotNull
    private String productId;
    @NotNull
    private String productName;
    @NotNull
    private String productDescription;
    @NotNull
    private String categoryProduct;
    @NotNull
    private Integer quantityProduct;
    @NotNull
    private BigDecimal productPrice;
}
