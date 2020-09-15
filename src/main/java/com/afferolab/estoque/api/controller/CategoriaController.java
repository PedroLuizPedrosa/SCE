package com.afferolab.estoque.api.controller;

import java.util.List;

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

import com.afferolab.estoque.api.dto.CategoriaDTO;
import com.afferolab.estoque.exception.RegraNegocioException;
import com.afferolab.estoque.model.entity.Categoria;
import com.afferolab.estoque.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping
	public ResponseEntity buscar( 
		@RequestParam(value="nome", required = false) String nome){
		Categoria categoriaFiltro = new Categoria();
		categoriaFiltro.setNome(nome);
		List<Categoria> categorias = categoriaService.buscarCategoria(categoriaFiltro);
		return ResponseEntity.ok(categorias);
	}
	
	@GetMapping("{id}")
	public ResponseEntity obterCategoria (@PathVariable("id") Long id) {
		return categoriaService.obterPorId(id)
				.map (categoria -> new ResponseEntity(converter(categoria), HttpStatus.OK))
				.orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND) );
	}
	
	@PostMapping
	public ResponseEntity salvar( @RequestBody CategoriaDTO dto ) {
		try {
			Categoria entidade = converter(dto);
			entidade = categoriaService.salvarCategoria(entidade);
			return new ResponseEntity(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity deletar( @PathVariable("id") Long id ) {
		return categoriaService.obterPorId(id).map( entidade -> {
			categoriaService.deletarCategoria(entidade);
			return new ResponseEntity( HttpStatus.NO_CONTENT );
		}).orElseGet( () -> 
			new ResponseEntity("Categoria não encontrado na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	@PutMapping("{id}")
	public ResponseEntity atualizarCategoria( @PathVariable("id") Long id, @RequestBody CategoriaDTO dto ) {
		return categoriaService.obterPorId(id).map( entity -> {
			try {
				Categoria categoria = converter(dto);
				categoria.setId(entity.getId());
				categoriaService.atualizarCategoria(categoria);
				return ResponseEntity.ok(categoria);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet( () ->
			new ResponseEntity("Categoria não encontrada na base de Dados.", HttpStatus.BAD_REQUEST) );
	}
	
	private CategoriaDTO converter(Categoria categoria) {
		return CategoriaDTO
				.builder()
				.id(categoria.getId())
				.nome(categoria.getNome()).
				build();
	}
	
	private Categoria converter(CategoriaDTO dto) {
		Categoria categoria = new Categoria();
		categoria.setId(dto.getId());
		categoria.setNome(dto.getNome());
		System.out.println(categoria.getId());
		System.out.println(categoria.getNome());
		return categoria;
	}

}
