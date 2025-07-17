package com.deliverytech.delivery_api.services.impl;

import com.deliverytech.delivery_api.dto.response.UsuarioResponseDTO;
import com.deliverytech.delivery_api.entity.Usuario;
import com.deliverytech.delivery_api.repository.UsuarioRepository;
import com.deliverytech.delivery_api.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioResponseDTO findByEmail(String email) {
        Optional<Usuario> byEmail = usuarioRepository.findByEmail(email);

        return modelMapper.map(byEmail, UsuarioResponseDTO.class);
    }

    @Override
    public UsuarioResponseDTO save(Usuario usuario) {
        // Verifica se o email já está cadastrado
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + usuario.getEmail());
        }

        // Salva o usuário no repositório
        Usuario savedUsuario = usuarioRepository.save(usuario);

        // Converte a entidade para DTO de resposta
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(savedUsuario.getId());
        response.setEmail(savedUsuario.getEmail());
        response.setNome(savedUsuario.getNome());
        response.setAtivo(savedUsuario.getAtivo());

        return response;
    }

    @Override
    public UsuarioResponseDTO update(Usuario usuario) {
        // Verifica se o usuário existe
        Optional<Usuario> existingUsuario = usuarioRepository.findById(usuario.getId());
        if (!existingUsuario.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado: " + usuario.getId());
        }

        // Atualiza os dados do usuário
        Usuario updatedUsuario = usuarioRepository.save(usuario);

        // Converte a entidade atualizada para DTO de resposta
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(updatedUsuario.getId());
        response.setEmail(updatedUsuario.getEmail());
        response.setNome(updatedUsuario.getNome());
        response.setAtivo(updatedUsuario.getAtivo());

        return response;
    }

    @Override
    public void delete(Long id) {
        // Verifica se o usuário existe
        Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
        if (!existingUsuario.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado: " + id);
        }

        // Deleta o usuário
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        // Busca o usuário pelo ID
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        // Se o usuário não for encontrado, retorna null
        if (!usuario.isPresent()) {
            return null;
        }

        // Converte a entidade para DTO de resposta
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(usuario.get().getId());
        response.setEmail(usuario.get().getEmail());
        response.setNome(usuario.get().getNome());
        response.setAtivo(usuario.get().getAtivo());

        return response;
    }

    public UsuarioResponseDTO loadUserByUsername(String username) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        Usuario usuario = usuarioOptional.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getRole().name()) // Supondo que Role seja um enum
                .disabled(!usuario.getAtivo()) // Desabilita se não estiver ativo
                .build();
    }
}
