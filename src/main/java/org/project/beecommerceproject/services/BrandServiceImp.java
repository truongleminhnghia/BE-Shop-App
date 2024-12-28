package org.project.beecommerceproject.services;

import org.project.beecommerceproject.dtos.requests.BrandRequest;
import org.project.beecommerceproject.entities.Brand;
import org.project.beecommerceproject.enums.ErrorCode;
import org.project.beecommerceproject.exceptions.AppException;
import org.project.beecommerceproject.mappers.BrandMapper;
import org.project.beecommerceproject.repositories.BrandRepository;
import org.project.beecommerceproject.src.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImp implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public Brand save(BrandRequest request) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (!checkAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        if (brandRepository.existsByBrandName(request.getBrandName())
                || brandRepository.findByBrandName(request.getBrandName()) != null) {
            throw new AppException(ErrorCode.BRAND_EXISTING);
        }
        Brand brand = brandMapper.toBrand(request);
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(String id) {
        return brandRepository.findById(id.trim()).orElseThrow(() -> new AppException(ErrorCode.BRAND_DO_NOT_EXIST));
    }

    @Override
    public List<Brand> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        if (brands.isEmpty() || brands == null) {
            throw new AppException(ErrorCode.LIST_IS_EMPTY);
        }
        return brands;
    }

    @Override
    public boolean update(String id, BrandRequest request) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (!checkAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        boolean result = false;
        Brand brand = getById(id);
        if (brand == null) {
            throw new AppException(ErrorCode.BRAND_DO_NOT_EXIST);
        } else {
            if (request.getBrandName() != null) {
                brand.setBrandName(request.getBrandName());
            } else if (request.getDescription() != null) {
                brand.setDescription(request.getDescription());
            } else if (!request.isStatus()) {
                brand.setStatus(false);
            }
            brandRepository.save(brand);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean checkAdmin = AppUtils.checkAdmin();
        if (!checkAdmin) {
            throw new AppException(ErrorCode.NO_ACCESS);
        }
        boolean result = false;
        Brand brand = getById(id);
        if (brand == null) {
            throw new AppException(ErrorCode.BRAND_DO_NOT_EXIST);
        } else {
            brandRepository.delete(brand);
            result = true;
        }
        return result;
    }

    @Override
    public List<Brand> getByStatus(Boolean status) {
        List<Brand> brands = brandRepository.findByStatus(status);
        if (brands.isEmpty()) {
            throw new AppException(ErrorCode.LIST_IS_EMPTY);
        }
        return brands;
    }

    @Override
    public Brand getByName(String name) {
        return null;
    }

    @Override
    public List<Brand> getByNameContaining(String name) {
        return List.of();
    }

    @Override
    public List<Brand> getByNameContainingAndStatus(String name, Boolean status) {
        return List.of();
    }
}
