package com.antonio.spring.controladores;

import com.antonio.spring.modelo.Empleado;
import com.antonio.spring.servicios.EmpleadoServicio;
import com.antonio.spring.upload.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ControladorEmpleado {
    @Autowired
    public EmpleadoServicio servicio;
    @Autowired
    private StorageService storageService;

    private String avatarEnviado;

    @GetMapping("/empleados")
    public String listado(Model model) {
        model.addAttribute("empleadosLista", servicio.findAll());
        return "listEmp";
    }

    @GetMapping({"/empleado/new"})
    public String nuevoEmpleado(Model model) {
        model.addAttribute("empleadoForm", new Empleado());
        return "formEmp";
    }

    @PostMapping("/empleado/new/submit")
    public String nuevoEmpleadoSubmit(
            @Valid @ModelAttribute("empleadoForm")
                    Empleado empleado, BindingResult result) {
        if (result.hasErrors()) {
            return "formEmp";
        } else {
            servicio.add(empleado);
        }
        return "redirect:/empleados";
    }
}
