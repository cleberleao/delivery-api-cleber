package com.deliverytech.delivery_api.controller;
import com.deliverytech.delivery_api.dto.ProdutoRequestDTO;
import com.deliverytech.delivery_api.dto.ProdutoResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery_api.services.ProdutoService;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produto = produtoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO produto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @PatchMapping("/{id}/ativar-desativar")
    public ResponseEntity<ProdutoResponseDTO> ativarDesativarProduto(@PathVariable Long id) {
        ProdutoResponseDTO produtoAtualizado = produtoService.ativarDesativarProduto(id);
        return ResponseEntity.ok(produtoAtualizado);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorNome(@PathVariable String nome) {
        ProdutoResponseDTO produto = produtoService.buscarPorNome(nome);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorRestaurante(@PathVariable Long restauranteId) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorRestaurante(restauranteId);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorCategoria(@PathVariable String categoria) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorCategoria(categoria);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping("/preco")
    public ResponseEntity<List<ProdutoResponseDTO>>buscarPorPreco(@RequestParam BigDecimal precoMinimo, @RequestParam BigDecimal precoMaximo) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorPreco(precoMinimo, precoMaximo);
        return ResponseEntity.ok(produtos);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscarTodosProdutos() {
        List<ProdutoResponseDTO> produtos = produtoService.buscarTodosProdutos();
        return ResponseEntity.ok(produtos);
    }
    // preço menor ou igual a 20.00
    @GetMapping("/preco/{valor}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorPrecoMenorOuIgual(@PathVariable BigDecimal valor) {
        List<ProdutoResponseDTO> produtos = produtoService.buscarPorPrecoMenorOuIgual(valor);
        return ResponseEntity.ok(produtos);
    }

}
