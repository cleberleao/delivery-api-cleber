package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.LoginRequestDTO;
import com.deliverytech.delivery_api.dto.request.RegisterRequestDTO;
import com.deliverytech.delivery_api.dto.response.LoginResponseDTO;
import com.deliverytech.delivery_api.dto.response.UsuarioResponseDTO;
import com.deliverytech.delivery_api.entity.Usuario;
import com.deliverytech.delivery_api.enums.Role;
import com.deliverytech.delivery_api.repository.UsuarioRepository;
import com.deliverytech.delivery_api.security.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Operações de autenticação e registro de usuários")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/register")
    @Operation(summary = "Registrar usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        // Verifica se o email já está cadastrado
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Usuario usuario = Usuario.builder()
                .email(request.getEmail())
                .senha(passwordEncoder.encode(request.getSenha()))
                .nome(request.getNome())
                .role(request.getRole() != null ? request.getRole() : Role.CLIENTE)
                .dataCriacao(LocalDateTime.now())
                .ativo(true)
                .restauranteId(request.getRestauranteId())
                .build();

        usuarioRepository.save(usuario);
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUsuario(modelMapper.map(usuario, UsuarioResponseDTO.class));
        response.setTipo("Bearer");
        response.setExpiracao(86400000L); // 1 dia de expiração
        response.setToken(jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais ou Token inválidos"),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        LoginResponseDTO response = new LoginResponseDTO();
        response.setUsuario(modelMapper.map(usuario, UsuarioResponseDTO.class));
        response.setTipo("Bearer");
        response.setExpiracao(86400000L); // 1 dia de expiração
        response.setToken(jwtUtil.generateToken(User.withUsername(usuario.getEmail()).password(usuario.getSenha()).authorities("ROLE_" + usuario.getRole().name()).build(), usuario));
        return ResponseEntity.ok(response);
    }
}

