package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    boolean existsById(String id);
    Product findByProductName(String name);
    List<Product> findByProductNameContainingIgnoreCase(String name);
    List<Product> findByProductNameContainingAndStatus(String productName, Boolean status);
    List<Product> findByStatus(boolean status);
//    List<Product> finndByBrand(String brand);
}
