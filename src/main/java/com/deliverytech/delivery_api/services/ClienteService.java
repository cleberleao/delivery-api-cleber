package com.deliverytech.delivery_api.services;

import java.util.List;

import com.deliverytech.delivery_api.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;
import org.springframework.data.domain.Page;

public interface ClienteService {

    ClienteResponseDTO cadastrar(ClienteRequestDTO dto);

    ClienteResponseDTO buscarPorId(Long id);

    ClienteResponseDTO buscarPorEmail(String email);

    ClienteResponseDTO atualizar(Long id, ClienteRequestDTO dto);

    ClienteResponseDTO ativarDesativarCliente(Long id);

    List<ClienteResponseDTO> listarAtivos();

    //listar ativos paginado
    Page<ClienteResponseDTO> listarAtivosPaginado(int page, int size);

    List<ClienteResponseDTO> buscarPorNome(String nome);
    
}