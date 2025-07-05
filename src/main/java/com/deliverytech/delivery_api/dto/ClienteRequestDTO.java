package com.deliverytech.delivery_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "O telefone deve ser um número válido com 10 a 15 dígitos, podendo iniciar com '+'")
    private String telefone;
    
    @NotBlank(message = "O endereço é obrigatório")
    private String endereco;
    
}
