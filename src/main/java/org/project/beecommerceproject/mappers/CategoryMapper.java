package org.project.beecommerceproject.mappers;

import org.mapstruct.Mapper;
import org.project.beecommerceproject.dtos.requests.CategoryRequest;
import org.project.beecommerceproject.dtos.responses.CategoryResponse;
import org.project.beecommerceproject.entities.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(CategoryRequest request);
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
