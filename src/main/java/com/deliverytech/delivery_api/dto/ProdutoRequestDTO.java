package com.deliverytech.delivery_api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProdutoRequestDTO {

    @NotNull(message = "O Nome do produto é obrigatório")
    private String nome;

    @NotNull(message = "A descrição do produto é obrigatória")
    private String descricao;

    @NotNull(message = "O preço do produto é obrigatório")
    private BigDecimal preco;

    @NotNull(message = "A categoria do produto é obrigatória")
    private String categoria;

    @NotNull(message = "A disponibilidade do produto é obrigatória")
    private Boolean disponivel;

    private Long restauranteId;

}