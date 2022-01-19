package com.antonio.spring.servicios;

import com.antonio.spring.modelo.Empleado;
import com.antonio.spring.repositorios.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Service
public class EmpleadoServicio {
    @Autowired
    private EmpleadoRepositorio repositorio;

    public Empleado add(Empleado e) {
        repositorio.save(e);
        return e;
    }

    public List<Empleado> findAll() {
        return (List<Empleado>) repositorio.findAll();
    }

    public Empleado findById(long id) {
        return repositorio.findById(id).orElse(null);
    }

    public Empleado edit(Empleado e) {
        repositorio.save(e);
        return e;
    }

    public void deleteEmpleado(long id) {
        repositorio.deleteById(id);
    }
}
