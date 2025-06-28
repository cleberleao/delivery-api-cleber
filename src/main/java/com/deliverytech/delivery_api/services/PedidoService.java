package com.deliverytech.delivery_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.entity.Pedido;
import com.deliverytech.delivery_api.repository.PedidoRepository;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido cadastrar(Pedido pedido) {

        return pedidoRepository.save(pedido);
    }
    
}
