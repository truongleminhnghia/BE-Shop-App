package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.ProductCreationRequest;
import org.project.beecommerceproject.entities.Product;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.ProductMapper;
import org.project.beecommerceproject.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public Product create(ProductCreationRequest request) {
        if (productRepository.findByProductName(request.getProductName()) != null) {
            throw new AppException(ErrorCode.PRODUCT_EXISTING);
        }
        Product product = productMapper.toProduct(request);

        return null;
    }

    @Override
    public Product getById(String id) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public List<Product> getByNameContains(String name) {
        return List.of();
    }

    @Override
    public List<Product> getByCategoryName(String categoryName) {
        return List.of();
    }

    @Override
    public Product getByName(String name) {
        return null;
    }

    @Override
    public boolean update(String id, ProductCreationRequest request) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<Product> getByBrandName(String brandName) {
        return List.of();
    }

    @Override
    public List<Product> getByStatus(boolean status) {
        return List.of();
    }
}
