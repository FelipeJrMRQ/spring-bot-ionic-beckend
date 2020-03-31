package com.felipe.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.Pedido;
import com.felipe.cursomc.repositories.PedidoRepository;
import com.felipe.cursomc.servvices.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFoundException("Objeto não encontrado id: " +id+ " , Tipo: "+Pedido.class.getName()));
	}
}
