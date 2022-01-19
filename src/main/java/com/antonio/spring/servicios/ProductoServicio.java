package com.antonio.spring.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.spring.modelo.Producto;
import com.antonio.spring.repositorios.ProductoRepositorio;

@Service
public class ProductoServicio {

	//private List<Producto> repositorio = new ArrayList<>();
	@Autowired
	private ProductoRepositorio repositorio;
	
	public Producto add(Producto e) {
		repositorio.save(e);
		return e;
	}
	
	public List<Producto> findAll() {
		return (List<Producto>) repositorio.findAll();
	}
	
	public Producto findById(long id) {		
		return repositorio.findById(id).orElse(null);
	}
	
	public Producto edit(Producto e) {
		repositorio.save(e);		
		return e;
	}

    public void deleteProducto(long id) {
        repositorio.deleteById(id);
    }

}
