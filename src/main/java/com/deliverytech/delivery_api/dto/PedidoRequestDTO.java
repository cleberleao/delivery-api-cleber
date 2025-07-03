package com.deliverytech.delivery_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "O cliente é obrigatório")
    private Long id;
    @NotNull(message = "O restaurante é obrigatório")
    private Long restauranteId;

    @NotNull(message = "Os itens são obrigatórios")
    private List<ItemPedidoRequestDTO> itens; // JSON ou string representando os itens do pedido

}
