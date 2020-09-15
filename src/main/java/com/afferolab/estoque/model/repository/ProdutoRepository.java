package com.afferolab.estoque.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afferolab.estoque.model.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
