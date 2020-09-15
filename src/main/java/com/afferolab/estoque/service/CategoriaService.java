package com.afferolab.estoque.service;

import java.util.List;
import java.util.Optional;

import com.afferolab.estoque.model.entity.Categoria;

public interface CategoriaService {
	
	Categoria salvarCategoria (Categoria categoria);
	
	Categoria atualizarCategoria (Categoria categoria);
	
	void deletarCategoria (Categoria categoria );
	
	List<Categoria> buscarCategoria(Categoria categoriaFiltro);
	
	void validar (Categoria categoria);
	
	Optional<Categoria> obterPorId(Long id);

}
