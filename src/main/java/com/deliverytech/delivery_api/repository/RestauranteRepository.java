package com.deliverytech.delivery_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.deliverytech.delivery_api.entity.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository <Restaurante, Long>{
    //Buscar por nome, categoria, ativos, ordenação por avaliação

     // Contar clientes ativos
   // @Query("SELECT COUNT(c) FROM restaurante c WHERE c.ativo = true")
   // Long countRestauranteAtivos();
    
    // Buscar Restaurantes por nome (contendo)
    List<Restaurante> findByNomeContainingIgnoreCase(String nome);
}
