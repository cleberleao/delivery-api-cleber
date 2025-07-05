package com.deliverytech.delivery_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(
    description = "Dados necessários para criar ou atualizar um restaurante",
    title = "Restaurante Request DTO")
public class RestauranteRequestDTO {

    @Schema(description = "Nome do restaurante", example = "Pizzaria Express", required = true)
    @NotNull(message = "O nome do restaurante é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do restaurante deve ter entre 3 e 100 caracteres")
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
