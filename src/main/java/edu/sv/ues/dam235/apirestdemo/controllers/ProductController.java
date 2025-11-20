package edu.sv.ues.dam235.apirestdemo.controllers;

import edu.sv.ues.dam235.apirestdemo.dtos.ProductCreateDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;
import edu.sv.ues.dam235.apirestdemo.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServices productServices;
    public ProductController(ProductServices productServices) { this.productServices = productServices; }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAll() {
        List<ProductsDTO> items = productServices.getAllProducts();
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }

    @PostMapping
    public ResponseEntity<ProductsDTO> create(@RequestBody ProductCreateDTO dto) {
        ProductsDTO created = productServices.createProduct(dto);
        return ResponseEntity.created(URI.create("/products/" + created.getCode())).body(created);
    }
}
