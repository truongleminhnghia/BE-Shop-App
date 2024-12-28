package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.ProductCreationRequest;
import org.project.beecommerceproject.entities.Product;

import java.util.List;

public interface ProductService {
    Product create(ProductCreationRequest request);
    Product getById(String id);
    List<Product> getAllProducts();
    List<Product> getByNameContains(String name);
    List<Product> getByCategoryName(String categoryName);
    Product getByName(String name);
    boolean update(String id, ProductCreationRequest request);
    boolean delete(String id);
    List<Product> getByBrandName(String brandName);
    List<Product> getByStatus(boolean status);
}
