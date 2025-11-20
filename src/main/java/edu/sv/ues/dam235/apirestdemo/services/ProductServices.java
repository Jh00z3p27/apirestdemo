package edu.sv.ues.dam235.apirestdemo.services;


import edu.sv.ues.dam235.apirestdemo.dtos.ProductCreateDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;

import java.util.List;

public interface ProductServices {
    List<ProductsDTO> getAllProducts();
    ProductsDTO createProduct(ProductCreateDTO dto);
}
