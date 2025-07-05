package com.deliverytech.delivery_api.controller;

import com.deliverytech.delivery_api.dto.ApiResponseWrapper;
import com.deliverytech.delivery_api.dto.PagedResponseWrapper;
import com.deliverytech.delivery_api.dto.RestauranteRequestDTO;
import com.deliverytech.delivery_api.dto.RestauranteResponseDTO;
import com.deliverytech.delivery_api.projection.RelatorioVendas;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery_api.services.RestauranteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/restaurantes")
@CrossOrigin(origins = "*")
@Tag(name = "Restaurantes", description = "Operações relacionadas aos restaurantes")
public class RestauranteController {
    
    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    @Operation(summary = "Cadastrar restaurante",
               description = "Cria um novo restaurante no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "Restaurante já existe")
    })
    public ResponseEntity<RestauranteResponseDTO> cadastrar(@Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO restaurante = restauranteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
    }

    @GetMapping
    @Operation(summary = "Listar pedidos",
            description = "Lista pedidos com filtros opcionais e paginação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso")
    })
    public ResponseEntity<ApiResponseWrapper<List<RestauranteResponseDTO>>> listarTodos(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @Parameter(description = "Parâmetros de paginação")
            Pageable pageable) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.listarAtivos();
        ApiResponseWrapper<List<RestauranteResponseDTO>> response = new ApiResponseWrapper<>(true, restaurantes, "Busca Realizada com sucesso");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorId(@PathVariable Long id) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorId(id);
        return ResponseEntity.ok(restaurante);
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestauranteResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody RestauranteRequestDTO dto) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.atualizar(id, dto);
        return ResponseEntity.ok(restauranteAtualizado);
    }
    @PatchMapping("/{id}/ativar-desativar")
    public ResponseEntity<RestauranteResponseDTO> ativarDesativarRestaurante(@PathVariable Long id) {
        RestauranteResponseDTO restauranteAtualizado = restauranteService.ativarDesativarRestaurante(id);
        return ResponseEntity.ok(restauranteAtualizado);
    }
    @GetMapping("/nome/{nome}")
    public ResponseEntity<RestauranteResponseDTO> buscarPorNome(@PathVariable String nome) {
        RestauranteResponseDTO restaurante = restauranteService.buscarPorNome(nome);
        return ResponseEntity.ok(restaurante);
    }
    @GetMapping("/preco/{precoMinimo}/{precoMaximo}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorPreco(@PathVariable BigDecimal precoMinimo, @PathVariable BigDecimal precoMaximo) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarPorPreco(precoMinimo, precoMaximo);
        return ResponseEntity.ok(restaurantes);
    }
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(restaurantes);
    }

    //inativar restaurante
    @PatchMapping("/{id}/inativar")
    public ResponseEntity<RestauranteResponseDTO> inativarRestaurante(@PathVariable Long id) {
        RestauranteResponseDTO restauranteInativado = restauranteService.inativarRestaurante(id);
        return ResponseEntity.ok(restauranteInativado);
    }
    //listar taxa de entrega menor ou igual
    @GetMapping("/taxa-entrega")
    public ResponseEntity<List<RestauranteResponseDTO>> buscarPorTaxaEntrega(@RequestParam BigDecimal taxa) {
        List<RestauranteResponseDTO> restaurantes = restauranteService.buscarPorTaxaEntrega(taxa);
        return ResponseEntity.ok(restaurantes);
    }
    // Listar os 5 primeiros restaurantes por nome
    @GetMapping("/top-cinco")
    public ResponseEntity<List<RestauranteResponseDTO>> listarTop5PorNome() {
        List<RestauranteResponseDTO> top5Restaurantes = restauranteService.listarTop5PorNome();
        return ResponseEntity.ok(top5Restaurantes);
    }
    //relatorio-vendas
    @GetMapping("/relatorio-vendas")
    public ResponseEntity<List<RelatorioVendas>> relatorioVendasPorRestaurante() {
        List<RelatorioVendas> relatorio = restauranteService.relatorioVendasPorRestaurante();
        return ResponseEntity.ok(relatorio);
    }

}
