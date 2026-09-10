package com.sdetcommerce.backend.service;

import com.sdetcommerce.backend.dto.ProductRequest;
import com.sdetcommerce.backend.dto.ProductResponse;
import com.sdetcommerce.backend.entity.Product;
import com.sdetcommerce.backend.exception.ProductNotFoundException;
import com.sdetcommerce.backend.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(
            ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    // CREATE
    public ProductResponse createProduct(
            ProductRequest request) {

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStock()
        );

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    // GET ALL
    public List<ProductResponse> getAllProducts() {

        return productRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // GET BY ID
    public ProductResponse getProductById(Long id) {

        Product product = findProductById(id);

        return mapToResponse(product);
    }

    // UPDATE
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        Product product = findProductById(id);

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    // DELETE
    public void deleteProduct(Long id) {

        Product product = findProductById(id);

        productRepository.delete(product);
    }
    // SEARCH PRODUCTS BY NAME
public List<ProductResponse> searchProducts(
        String name) {

    return productRepository
            .findByNameContainingIgnoreCase(name)
            .stream()
            .map(this::mapToResponse)
            .toList();
}

    // COMMON PRODUCT LOOKUP
    private Product findProductById(Long id) {

        return productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id
                        )
                );
    }

    // ENTITY -> RESPONSE DTO
    private ProductResponse mapToResponse(
            Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}