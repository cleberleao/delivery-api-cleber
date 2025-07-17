package com.deliverytech.delivery_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String role;
    private boolean ativo;
    private LocalDateTime dataCriacao;
    private Long restauranteId;

}
