package org.project.beecommerceproject.mappers;

import org.mapstruct.Mapper;
import org.project.beecommerceproject.dtos.requests.BrandRequest;
import org.project.beecommerceproject.dtos.responses.BrandResponse;
import org.project.beecommerceproject.entities.Brand;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    Brand toBrand(BrandRequest request);
    BrandResponse toBrandResponse(Brand brand);
    List<BrandResponse> toBrandResponses(List<Brand> brands);
}
