package com.antonio.spring.repositorios;

import com.antonio.spring.modelo.Empleado;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadoRepositorio extends CrudRepository<Empleado, Long> {
}
