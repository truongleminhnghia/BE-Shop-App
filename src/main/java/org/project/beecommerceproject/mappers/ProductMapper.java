package org.project.beecommerceproject.mappers;

import org.mapstruct.Mapper;
import org.project.beecommerceproject.dtos.requests.ProductCreationRequest;
import org.project.beecommerceproject.dtos.responses.ProductResponse;
import org.project.beecommerceproject.entities.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toProduct(ProductCreationRequest request);
    ProductResponse toProductResponse(Product product);
    List<ProductResponse> toProductResponseList(List<Product> products);
}
