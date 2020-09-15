package com.afferolab.estoque.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afferolab.estoque.model.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
