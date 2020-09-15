package com.afferolab.estoque.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	
	private Long id;
	private String codBarras;
	private String nome;
	private String descricao;
	private Integer quantidade;
	private Long idCategoria;
}
