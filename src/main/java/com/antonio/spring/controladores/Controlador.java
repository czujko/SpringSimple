package com.antonio.spring.controladores;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.antonio.spring.modelo.Producto;
import com.antonio.spring.servicios.ProductoServicio;
import com.antonio.spring.upload.storage.StorageService;



@Controller
public class Controlador {
	@Autowired
	public ProductoServicio servicio;
	
	@Autowired
	private StorageService storageService;
	
	private String ficheroEnviado; //

	@GetMapping({ "/", "/productos" })
	public String listado(Model model) {
		model.addAttribute("listaProductos", servicio.findAll());
		return "listado";
	}

	@GetMapping("/producto/new")
	public String nuevoEmpleadoForm(Model model) {
		model.addAttribute("productoForm", new Producto());
		return "form";
	}

	@PostMapping("/producto/new/submit")
	public String nuevoProductoSubmit(@Valid @ModelAttribute("productoForm") Producto nuevoProducto,
			BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

		if (bindingResult.hasErrors()) {
			return "form";
		} else {
			if (!file.isEmpty()) {
				String imagen = storageService.store(file, nuevoProducto.getNombre());
				System.out.println("La imagen a guardar es : " + imagen);
				nuevoProducto.setImagen(MvcUriComponentsBuilder
						.fromMethodName(Controlador.class, "serveFile", imagen).build().toUriString());
			}
			servicio.add(nuevoProducto);
			return "redirect:/productos";
		}
	}

	@GetMapping("/producto/edit/{id}")
	public String editarProductoForm(@PathVariable long id, Model model) {
		Producto producto = servicio.findById(id);
		if (producto != null) {
			ficheroEnviado=producto.getImagen(); //para controlar si cambia el fichero
			model.addAttribute("productoForm", producto);
			return "form";
		} else {
			return "redirect:/producto/new";
		}
	}
	
	@PostMapping("/producto/edit/submit")
	public String editarProductosPost(@Valid @ModelAttribute("productoForm") Producto nuevoProducto,
			BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

		System.out.println(ficheroEnviado + ", " + file.getName());
		if (bindingResult.hasErrors()) {			
			return "form";	
		} else {			
			if (!file.isEmpty()) {
				String imagen = storageService.store(file, nuevoProducto.getNombre());
				nuevoProducto.setImagen(MvcUriComponentsBuilder
						.fromMethodName(Controlador.class, "serveFile", imagen).build().toUriString());
			}else {
				nuevoProducto.setImagen(ficheroEnviado);
			}
			servicio.edit(nuevoProducto);
			return "redirect:/productos";
		}
	}
	
    @GetMapping("/producto/delete/{id}")
    public String deleteProducto(@PathVariable("id") int id){
        servicio.deleteProducto(id);
        return "redirect:/productos";
    }
    
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().body(file);
	}

}
