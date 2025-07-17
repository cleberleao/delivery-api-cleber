package com.deliverytech.delivery_api.services;

import com.deliverytech.delivery_api.dto.response.UsuarioResponseDTO;
import com.deliverytech.delivery_api.entity.Usuario;

public interface UsuarioService {

    // Define os métodos que serão implementados pela classe de serviço
    UsuarioResponseDTO findByEmail(String email);

    UsuarioResponseDTO save(Usuario usuario);

    UsuarioResponseDTO update(Usuario usuario);

    void delete(Long id);

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO loadUserByUsername(String email);
}
