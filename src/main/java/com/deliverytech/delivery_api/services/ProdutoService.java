package com.deliverytech.delivery_api.services;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deliverytech.delivery_api.entity.Produto;
import com.deliverytech.delivery_api.entity.ProdutoDTO;
import com.deliverytech.delivery_api.entity.Restaurante;
import com.deliverytech.delivery_api.repository.ProdutoRepository;
import com.deliverytech.delivery_api.repository.RestauranteRepository;

@Service
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    /**
     * Cadastrar novo produto
     */
    public Produto cadastrar(Produto produto, Long restauranteId) {
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado: " + restauranteId));

        validarDadosProduto(produto);

        produto.setRestaurante(restaurante);
        produto.setDisponivel(restaurante.getAtivo());

        return produtoRepository.save(produto);
    }

    /**
     * Buscar por ID
     */
    @Transactional(readOnly = true)
    public ProdutoDTO buscarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.map(Produto::getId).orElse(null));
        produtoDTO.setNome(produto.map(Produto::getNome).orElse(null));
        produtoDTO.setDescricao(produto.map(Produto::getDescricao).orElse(null));
        produtoDTO.setPreco(produto.map(Produto::getPreco).orElse(null));
        produtoDTO.setCategoria(produto.map(Produto::getCategoria).orElse(null));
        return produtoDTO;
    }

    /**
     * Listar produtos por restaurante
     */
    @Transactional(readOnly = true)
    public List<ProdutoDTO> listarPorRestaurante(Long restauranteId) {
        List<Produto> produto = produtoRepository.findByRestauranteIdAndDisponivelTrue(restauranteId);
        List<ProdutoDTO> produtoDTO = new ArrayList<>();
        for (Produto p : produto) {
            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(p.getId());
            dto.setNome(p.getNome());
            dto.setDescricao(p.getDescricao());
            dto.setPreco(p.getPreco());
            dto.setCategoria(p.getCategoria());
            produtoDTO.add(dto);
        }
        return produtoDTO;
    }

    /**
     * Buscar por categoria
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorCategoria(String categoria) {
        return produtoRepository.findByCategoriaAndDisponivelTrue(categoria);
    }

    /**
     * Atualizar produto
     */
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        validarDadosProduto(produtoAtualizado);

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setCategoria(produtoAtualizado.getCategoria());

        return produtoRepository.save(produto);
    }

    /**
     * Alterar disponibilidade
     */
    public void alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = produtoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));

        produto.setDisponivel(disponivel);
        produtoRepository.save(produto);
    }

    /**
     * Buscar por faixa de preço
     */
    @Transactional(readOnly = true)
    public List<Produto> buscarPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetweenAndDisponivelTrue(precoMin, precoMax);
    }

    private void validarDadosProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }

        if (produto.getPreco() == null || produto.getPreco().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }
}
