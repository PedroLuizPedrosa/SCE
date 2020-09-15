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
import com.afferolab.estoque.model.entity.Produto;
import com.afferolab.estoque.model.repository.CategoriaRepository;
import com.afferolab.estoque.model.repository.ProdutoRepository;
import com.afferolab.estoque.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService{

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	@Override
	@Transactional
	public Produto salvarProduto(Produto produto) {
		validar(produto);
		return produtoRepository.save(produto);
	}

	@Override
	@Transactional
	public Produto atualizarProduto(Produto produto) {
		Objects.requireNonNull(produto.getId());
		validar(produto);
		return produtoRepository.save(produto);
	}

	@Override
	@Transactional
	public void deletarProduto(Produto produto) {
		Objects.requireNonNull(produto.getId());
		produtoRepository.delete(produto);		
	}

	@Override
	@Transactional (readOnly = true)
	public List<Produto> buscarProduto(Produto produtoFiltro) {
		Example example = Example.of(produtoFiltro, 
				ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		
		return produtoRepository.findAll(example);
	}

	@Override
	public void validar(Produto produto) {
		if (produto.getCodBarras() == null || produto.getCodBarras().trim().equals("")) {
			throw new RegraNegocioException("Informe um Código de Barras válido.");
		}
		if (produto.getNome() == null || produto.getNome().trim().equals("")) {
			throw new RegraNegocioException("Informe o Nome do produto.");
		}
		if (produto.getQuantidade() == null || produto.getQuantidade() < 1 ) {
			throw new RegraNegocioException("Informe a Quantidade válida.");
		}
		if (produto.getCategoria()== null || produto.getCategoria().getId() == null) {
			throw new RegraNegocioException("Informe a Categoria.");
		}
	}

	@Override
	public Optional<Produto> obterPorId(Long id) {
		return produtoRepository.findById(id);
	}
	
}
