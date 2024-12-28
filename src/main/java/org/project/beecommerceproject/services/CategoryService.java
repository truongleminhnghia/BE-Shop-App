package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.CategoryRequest;
import org.project.beecommerceproject.entities.Category;

import java.util.List;

public interface CategoryService {
    Category save(CategoryRequest request);
    Category getById(String id);
    Category getByName(String name);
    List<Category> getAllCategories();
    boolean delete(String id);
    boolean update(String id, CategoryRequest request);
    List<Category> getByNameAndStatus(String name, boolean status);
    List<Category> getByNameContaining(String name);
    List<Category> getByStatus(boolean status);
}
