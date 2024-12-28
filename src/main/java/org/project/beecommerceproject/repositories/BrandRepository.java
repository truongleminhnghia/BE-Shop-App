package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    boolean existsById(String id);
    boolean existsByBrandName(String name);
    Brand findByBrandName(String brandName);
    List<Brand> findByStatus(boolean status);
    List<Brand> findByBrandNameContaining(String brandName);
    List<Brand> findByBrandNameContainingAndStatus(String brandName, boolean status);
    Brand findByBrandNameEqualsIgnoreCase(String brandName);

}
