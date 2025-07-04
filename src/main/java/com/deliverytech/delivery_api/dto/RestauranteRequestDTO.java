package com.deliverytech.delivery_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RestauranteRequestDTO {

    @NotNull(message = "O nome do restaurante é obrigatório")
    private String nome;

    @NotNull(message = "A categoria do restaurante é obrigatória")
    private String categoria;

    @NotNull(message = "O endereço do restaurante é obrigatório")
    private String endereco;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
    private String telefone;

    @NotNull(message = "A taxa de entrega do restaurante é obrigatória")
    private BigDecimal taxaEntrega;

    private BigDecimal avaliacao;

    @NotNull(message = "O status do restaurante é obrigatório")
    private Boolean ativo = true;

}
