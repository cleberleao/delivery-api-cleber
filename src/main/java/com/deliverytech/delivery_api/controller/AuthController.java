package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.request.LoginRequestDTO;
import com.deliverytech.delivery_api.dto.request.RegisterRequestDTO;
import com.deliverytech.delivery_api.dto.response.LoginResponseDTO;
import com.deliverytech.delivery_api.dto.response.UsuarioResponseDTO;
import com.deliverytech.delivery_api.entity.Usuario;
import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.security.SecurityUtils;
import com.deliverytech.delivery_api.services.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    private

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            // Autenticar usuário
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getSenha()
                    )
            );

            // Carregar detalhes do usuário
            UserDetails userDetails = authService.loadUserByUsername(loginRequest.getEmail());

            // Gerar token JWT
            String token = jwtUtil.generateToken(userDetails);

            // Criar resposta
            Usuario usuario = (Usuario) userDetails;
            UsuarioResponseDTO userResponse = new UsuarioResponseDTO(usuario);
            LoginResponseDTO loginResponse = new LoginResponseDTO(token, jwtExpiration, userResponse);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno do servidor");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        try {
            // Verificar se email já existe
            if (authService.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Email já está em uso");
            }

            // Criar novo usuário
            Usuario novoUsuario = authService.criarUsuario(registerRequest);

            // Retornar dados do usuário (sem token - usuário deve fazer login)
            UsuarioResponseDTO userResponse = new UsuarioResponseDTO(novoUsuario);
            return ResponseEntity.status(201).body(userResponse);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        try {
            Usuario usuarioLogado = SecurityUtils.getCurrentUser();
            UsuarioResponseDTO userResponse = new UsuarioResponseDTO(usuarioLogado);
            return ResponseEntity.ok(userResponse);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }
    }
}

