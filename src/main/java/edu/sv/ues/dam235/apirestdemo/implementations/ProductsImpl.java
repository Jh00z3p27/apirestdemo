package edu.sv.ues.dam235.apirestdemo.implementations;

import edu.sv.ues.dam235.apirestdemo.dtos.ProductCreateDTO;
import edu.sv.ues.dam235.apirestdemo.dtos.ProductsDTO;
import edu.sv.ues.dam235.apirestdemo.entities.Product;
import edu.sv.ues.dam235.apirestdemo.repositories.ProductRepository;
import edu.sv.ues.dam235.apirestdemo.services.ProductServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductsImpl implements ProductServices {

    private final ProductRepository productRepository;

    public ProductsImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductsDTO> getAllProducts() {
        List<ProductsDTO> result = new ArrayList<>();
        List<Product> items = productRepository.findAll();
        for (Product p : items) {
            result.add(new ProductsDTO(p.getCode(), p.getName(), p.isStatus()));
        }
        return result;
    }

    @Override
    public ProductsDTO createProduct(ProductCreateDTO dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setStatus(dto.isStatus());
        p = productRepository.save(p);
        return new ProductsDTO(p.getCode(), p.getName(), p.isStatus());
    }
}
