package com.afferolab.estoque.service;

import java.util.List;
import java.util.Optional;

import com.afferolab.estoque.model.entity.Produto;

public interface ProdutoService {

	Produto salvarProduto (Produto produto);
	
	Produto atualizarProduto (Produto produto);
	
	void deletarProduto (Produto produto);
	
	List<Produto> buscarProduto(Produto produto);
	
	void validar (Produto produto);
	
	Optional<Produto> obterPorId(Long id);
}
