package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    boolean existsByCategoryName(String name);
    Category findByCategoryName(String name);
    List<Category> findByCategoryNameStartsWith(String name);
    List<Category> findByStatus(boolean status);
    List<Category> findByCategoryNameContainingAndStatus(String name, boolean status);
}
