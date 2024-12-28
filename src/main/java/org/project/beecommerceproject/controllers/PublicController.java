package org.project.beecommerceproject.controllers;

import org.project.beecommerceproject.dtos.requests.RoleRequest;
import org.project.beecommerceproject.dtos.responses.ApiResponse;
import org.project.beecommerceproject.dtos.responses.CategoryResponse;
import org.project.beecommerceproject.entities.Category;
import org.project.beecommerceproject.entities.Role;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.CategoryMapper;
import org.project.beecommerceproject.services.CategoryService;
import org.project.beecommerceproject.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*")
public class PublicController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired private CategoryMapper categoryMapper;

    @GetMapping("/home")
    public String hello() {
        return "home";
    }

    @PostMapping("/create-role")
    public ResponseEntity<String> createRole(@RequestBody RoleRequest request) {
        Role role = roleService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @GetMapping("/get_all")
    public ResponseEntity<ApiResponse> getAll() {
        List<Category> categories = categoryService.getAllCategories();
        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.status(ErrorCode.LIST_IS_EMPTY.getCode())
                    .body(new ApiResponse(ErrorCode.LIST_IS_EMPTY.getCode().value(), false, ErrorCode.LIST_IS_EMPTY.getMessage(), null));
        }
        List<CategoryResponse> categoryResponses = categoryMapper.toCategoryResponseList(categories);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "list categories", categoryResponses));
    }

    @GetMapping("/get_by_name/category_name/{category_name}")
    public ResponseEntity<ApiResponse> getByName(@PathVariable("category_name") String name) {
        if (name.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NAME_NOT_NULL);
        }
        Category category = categoryService.getByName(name);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(401, false, "Category not found", null));
        }
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "Category by ID: " + name, categoryResponse));
    }

    @GetMapping("/get_by_id/category_id/{category_id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("category_id") String id) {
        if (id.trim().isEmpty()) {
            throw new AppException(ErrorCode.ID_IS_NOT_NULL);
        }
        Category category = categoryService.getById(id.trim());
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(401, false, "Category not found", null));
        }
        CategoryResponse categoryResponse = categoryMapper.toCategoryResponse(category);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "Category by ID: " + id, categoryResponse));
    }

    @GetMapping("/get_by_params")
    public ResponseEntity<ApiResponse> getByParams(
            @RequestParam(value = "category_name", required = false) String name,
            @RequestParam(value = "status", required = false) Boolean status
    ) {
        List<Category> categories = null;
        if (name == null && status == null) {
            categories = categoryService.getAllCategories();
        } else if (name != null && status != null) {
            categories = categoryService.getByNameAndStatus(name, status);
        } else if (name != null) {
            categories = categoryService.getByNameContaining(name);
        } else {
            categories = categoryService.getByStatus(status);
        }
        if (categories == null || categories.isEmpty()) {
            return ResponseEntity.status(ErrorCode.LIST_IS_EMPTY.getCode())
                    .body(new ApiResponse(ErrorCode.LIST_IS_EMPTY.getCode().value(), false, ErrorCode.LIST_IS_EMPTY.getMessage(), null));
        }
        List<CategoryResponse> categoryResponses = categoryMapper.toCategoryResponseList(categories);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(200, true, "Update category failed", categoryResponses));
    }
}
