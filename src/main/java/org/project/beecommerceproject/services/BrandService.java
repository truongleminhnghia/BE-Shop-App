package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.BrandRequest;
import org.project.beecommerceproject.entities.Brand;

import java.util.List;

public interface BrandService {
    Brand save(BrandRequest request);
    Brand getById(String id);
    List<Brand> getAllBrands();
    boolean update(String id, BrandRequest request);
    boolean delete(String id);
    List<Brand> getByStatus(Boolean status);
    Brand getByName(String name);
    List<Brand> getByNameContaining(String name);
    List<Brand> getByNameContainingAndStatus(String name, Boolean status);
}
