package com.example.productos.controller;

import com.example.productos.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoRepository repo;
    public ProductoController(ProductoRepository r){
        this.repo= r;
    }
   @GetMapping("{id}")
    public Producto get(@PathVariable Long id){
        return repo.findByidId(id).orElse(other.null);
   }

   @PostMapping
    public Producto crear(){
        return repo.save(new Producto(""))
   }
}
