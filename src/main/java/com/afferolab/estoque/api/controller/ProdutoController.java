package com.afferolab.estoque.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.afferolab.estoque.api.dto.ProdutoDTO;
import com.afferolab.estoque.exception.RegraNegocioException;
import com.afferolab.estoque.model.entity.Categoria;
import com.afferolab.estoque.model.entity.Produto;
import com.afferolab.estoque.service.CategoriaService;
import com.afferolab.estoque.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private CategoriaService categoriaService;

	@PostMapping
	public ResponseEntity salvar(@RequestBody ProdutoDTO dto) {
		try {
			Produto entidade = converter(dto);
			entidade = produtoService.salvarProduto(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PutMapping("{id}")
	public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
		return produtoService.obterPorId(id).map(entity -> {
			try {
				Produto produto = converter(dto);
				produto.setId(entity.getId());
				//produto.setCategoria(entity.getCategoria());
				//System.out.println("||| " + entity.getId() + " ||| " + entity.getCategoria());
				produtoService.atualizarProduto(produto);
				return ResponseEntity.ok(produto);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Produto não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity deletar(@PathVariable("id") Long id) {
		return produtoService.obterPorId(id).map(entidade -> {
			produtoService.deletarProduto(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Produto não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@GetMapping
	public ResponseEntity buscar(@RequestParam(value = "codBarras", required = false) String codBarras,
			@RequestParam(value = "nome", required = false) String nome,
			@RequestParam(value = "descricao", required = false) String descricao,
			@RequestParam(value = "quantidade", required = false) Integer quantidade,
			@RequestParam(value = "categoria", required = false) Long idCategoria) {

		Produto produtoFiltro = new Produto();
		produtoFiltro.setCodBarras(codBarras);
		produtoFiltro.setNome(nome);
		produtoFiltro.setDescricao(descricao);
		produtoFiltro.setQuantidade(quantidade);
		
		if (idCategoria != null) {
			Optional<Categoria> categoria = categoriaService.obterPorId(idCategoria);

			if (!categoria.isPresent()) {
				return ResponseEntity.badRequest().body("Categoria não encontrada.");
			} else {
				produtoFiltro.setCategoria(categoria.get());
			}
		}
		List<Produto> produtos = produtoService.buscarProduto(produtoFiltro);
		return ResponseEntity.ok(produtos);
	}

	private Produto converter(ProdutoDTO dto) {
		
		System.out.println("*** "+ dto.getIdCategoria()+ "  " + dto.getCodBarras()+ "  " + dto.getNome()+ "  " + dto.getDescricao()+ "  " + dto.getQuantidade()+ "  " + dto.getIdCategoria());
		
		Produto produto = new Produto();
		produto.setId(dto.getId());
		produto.setCodBarras(dto.getCodBarras());
		produto.setNome(dto.getNome());
		produto.setDescricao(dto.getDescricao());
		produto.setQuantidade(dto.getQuantidade());

		if (dto.getIdCategoria() != null) {
		Categoria categoria = categoriaService.obterPorId(dto.getIdCategoria())
				.orElseThrow(() -> new RegraNegocioException("Categoria não encontrada para o ID informado."));
		
		produto.setCategoria(categoria);
		}
		return produto;
	}

}
