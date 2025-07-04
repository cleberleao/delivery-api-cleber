package com.deliverytech.delivery_api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "O número do pedido é obrigatório")
    private String numeroPedido;

    @NotNull(message = "A data do pedido é obrigatória")
    private String dataPedido;

    @NotNull(message = "O valor do pedido é obrigatório")
    private BigDecimal valorTotal;

    private String observacoes;

    @NotNull(message = "O status do pedido é obrigatório")
    private Long clienteId;

    @NotNull(message = "O restaurante é obrigatório")
    private Long restauranteId;

    private String enderecoEntrega;

    @NotNull(message = "Os itens são obrigatórios")
    private List<ItemPedidoRequestDTO> itens;

}
