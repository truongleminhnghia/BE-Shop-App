package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.CategoryRequest;
import org.project.beecommerceproject.entities.Category;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.CategoryMapper;
import org.project.beecommerceproject.repositories.CategoryRepository;
import org.project.beecommerceproject.src.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Category save(CategoryRequest request) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (checkAdmin) {
            boolean checkExisting = categoryRepository.existsByCategoryName(request.getCategoryName());
            if (checkExisting) {
                throw new AppException(ErrorCode.CATEGORY_EXISTS);
            }
            Category category = categoryMapper.toCategory(request);
            return categoryRepository.save(category);
        } else {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
    }

    @Override
    public Category getById(String id) {
        Category category = categoryRepository.findById(id.trim())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_DO_NOT_EXIST));
        return category;
    }

    @Override
    public Category getByName(String name) {
        Category category = categoryRepository.findByCategoryName(name);
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_DO_NOT_EXIST);
        }
        return category;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_DO_NOT_EXIST);
        }
        return categories;
    }

    @Override
    public boolean delete(String id) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (!checkAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        boolean result = false;
        Category category = getById(id);
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_DO_NOT_EXIST);
        } else {
            categoryRepository.delete(category);
            result = true;
        }
        return result;
    }

    @Override
    public boolean update(String id, CategoryRequest request) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (!checkAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        boolean result = false;
        Category category = getById(id);
        if (category == null) {
            throw new AppException(ErrorCode.CATEGORY_DO_NOT_EXIST);
        } else {
            if (request.getCategoryName() != null) {
                category.setCategoryName(request.getCategoryName());
            }
            if (request.getDescription() != null) {
                category.setDescription(request.getDescription());
            }
            if (!request.isStatus()) {
                category.setStatus(false);
            }
            categoryRepository.save(category);
            result = true;
        }
        return result;
    }

    @Override
    public List<Category> getByNameAndStatus(String name, boolean status) {
        List<Category> categories = null;
        if ((name == null || name.isEmpty())) {
            categories = categoryRepository.findByStatus(status);
        } else {
            categories = categoryRepository.findByCategoryNameContainingAndStatus(name, status);
        }
        if (categories == null || categories.isEmpty()) {
            throw new AppException(ErrorCode.LIST_IS_EMPTY);
        }
        return categories;
    }

    @Override
    public List<Category> getByNameContaining(String name) {
        List<Category> categories = null;
        if (name == null || name.isEmpty()) {
            categories = categoryRepository.findAll();
        } else {
            categories = categoryRepository.findByCategoryNameStartsWith(name);
        }
        if (categories == null || categories.isEmpty()) {
            throw new AppException(ErrorCode.LIST_IS_EMPTY);
        }
        return categories;
    }

    @Override
    public List<Category> getByStatus(boolean status) {
        List<Category> categories = categoryRepository.findByStatus(status);
        if (categories == null || categories.isEmpty()) {
            throw new AppException(ErrorCode.LIST_IS_EMPTY);
        }
        return categories;
    }
}
