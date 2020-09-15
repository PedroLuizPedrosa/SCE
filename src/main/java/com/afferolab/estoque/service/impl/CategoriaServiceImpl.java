package com.afferolab.estoque.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.afferolab.estoque.exception.RegraNegocioException;
import com.afferolab.estoque.model.entity.Categoria;
import com.afferolab.estoque.model.repository.CategoriaRepository;
import com.afferolab.estoque.service.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
		super();
		categoriaRepository = categoriaRepository;
	}	

	@Override
	@Transactional
	public Categoria salvarCategoria(Categoria categoria) {
		validar(categoria);
		return categoriaRepository.save(categoria);
	}


	@Override
	@Transactional
	public Categoria atualizarCategoria(Categoria categoria) {
		Objects.requireNonNull(categoria.getId());
		validar(categoria);
		return categoriaRepository.save(categoria);
	}


	@Override
	@Transactional
	public void deletarCategoria(Categoria categoria) {
		Objects.requireNonNull(categoria.getId());
		categoriaRepository.delete(categoria);
	}


	@Override
	@Transactional (readOnly = true)
	public List<Categoria> buscarCategoria(Categoria categoriaFiltro) {
		Example example = Example.of(categoriaFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return categoriaRepository.findAll(example);
	}

	@Override
	public void validar(Categoria categoria) {
		if (categoria.getNome() == null || categoria.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe o Nome da categoria.");
		}
	}


	@Override
	public Optional<Categoria> obterPorId(Long id) {
		return categoriaRepository.findById(id);
	}
	
}
