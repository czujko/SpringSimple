package com.antonio.spring.repositorios;


import org.springframework.data.repository.CrudRepository;

import com.antonio.spring.modelo.Producto;

public interface ProductoRepositorio extends CrudRepository<Producto,Long> {
}
