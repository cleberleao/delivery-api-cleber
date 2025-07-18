package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.request.ClienteRequestDTO;
import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery_api.entity.Cliente;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClienteService")
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ModelMapper modelMapper;

    private Cliente cliente;

    private ClienteResponseDTO clienteResponseDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setEmail("joao@email.com");
        cliente.setTelefone("11999999999");
    }

    @Test
    @DisplayName("Deve salvar cliente com dados válidos")
    void should_SaveCliente_When_ValidData() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // When
        ClienteResponseDTO resultado = clienteService.cadastrar(modelMapper.map(cliente, ClienteRequestDTO.class));

        // Then
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        assertEquals("joao@email.com", resultado.getEmail());
        verify(clienteRepository).save(cliente);
        verify(clienteRepository).existsByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe")
    void should_ThrowException_When_EmailAlreadyExists() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.cadastrar(modelMapper.map(cliente, ClienteRequestDTO.class))
        );

        assertEquals("Email já cadastrado", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existe")
    void should_ThrowException_When_CpfAlreadyExists() {
        // Given
        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> clienteService.cadastrar(modelMapper.map(cliente, ClienteRequestDTO.class))
        );

        assertEquals("CPF já cadastrado", exception.getMessage());
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve buscar cliente por ID existente")
    void should_ReturnCliente_When_IdExists() {
        // Given
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // When
        ClienteResponseDTO resultado = clienteService.buscarPorId(1L);

        // Then
        assertEquals("João Silva", resultado.getNome());
        verify(clienteRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar vazio quando ID não existe")
    void should_ReturnEmpty_When_IdNotExists() {
        // Given
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        ClienteResponseDTO resultado = clienteService.buscarPorId(999L);

        // Then
        verify(clienteRepository).findById(999L);
    }

    @Test
    @DisplayName("Deve listar clientes com paginação")
    void should_ReturnPagedClientes_When_RequestedWithPagination() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Cliente> page = new PageImpl<>(Arrays.asList(cliente));
        when(clienteRepository.findAll(pageable)).thenReturn(page);

        // When
        Page<ClienteResponseDTO> resultado = clienteService.listarAtivosPaginado(pageable.getPageNumber(), pageable.getPageSize());

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.getTotalElements());
        assertEquals("João Silva", resultado.getContent().get(0).getNome());
        verify(clienteRepository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve atualizar cliente existente")
    void should_UpdateCliente_When_ClienteExists() {
        // Given
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("João Santos");
        clienteAtualizado.setEmail("joao.santos@email.com");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.existsByEmail("joao.santos@email.com")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteAtualizado);

        // When
        ClienteResponseDTO resultado = clienteService.atualizar(1L, modelMapper.map(clienteAtualizado, ClienteRequestDTO.class));

        // Then
        assertNotNull(resultado);
        assertEquals("João Santos", resultado.getNome());
        verify(clienteRepository).save(any(Cliente.class));
    }
}

