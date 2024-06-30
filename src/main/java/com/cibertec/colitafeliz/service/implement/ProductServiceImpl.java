package com.cibertec.colitafeliz.service.implement;

import com.cibertec.colitafeliz.JWT.JwtFilter;
import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.dao.ProductDao;
import com.cibertec.colitafeliz.entities.CategoryEntity;
import com.cibertec.colitafeliz.entities.ProductEntity;
import com.cibertec.colitafeliz.service.ProductService;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import com.cibertec.colitafeliz.wrapper.ProductWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProducts() {
        try {
            return new ResponseEntity<>(productDao.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(JwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_CREATED, HttpStatus.OK);
                }
                return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(JwtFilter.isAdmin()) {
                if (validateProductMap(requestMap, true)) {
                    Optional<ProductEntity> product = productDao.findById(UUID.fromString(requestMap.get("id")));
                    if (product.isPresent()) {
                        productDao.save(getProductFromMap(requestMap, true));
                        return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_UPDATED, HttpStatus.OK);
                    }
                    return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
                }
                return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(String id) {
        try {
            if(JwtFilter.isAdmin()) {
                UUID uuid = UUID.fromString(id);
                Optional<ProductEntity> product = productDao.findById(uuid);
                if (product.isPresent()) {
                    productDao.deleteById(uuid);
                    return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_DELETED, HttpStatus.OK);
                }
                return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
            return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(JwtFilter.isAdmin()) {
                if (requestMap.containsKey("id") && requestMap.containsKey("status")) {
                    UUID uuid = UUID.fromString(requestMap.get("id"));
                    Optional<ProductEntity> product = productDao.findById(uuid);
                    if (product.isPresent()) {
                        productDao.updateProductStatus(uuid, requestMap.get("status"));
                        return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_STATUS_UPDATED, HttpStatus.OK);
                    }
                    return GlobalUtils.getResponseEntity(GlobalConstants.PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
                }
                return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
            return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Long id) {
        try {
            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<ProductEntity> product = productDao.findById(uuid);
            if (product.isPresent()) {
                return new ResponseEntity<>(productDao.getProductById(uuid), HttpStatus.OK);
            }
            return new ResponseEntity<>(new ProductWrapper(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name") && requestMap.containsKey("categoryId")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private ProductEntity getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        ProductEntity product = new ProductEntity();
        CategoryEntity category = new CategoryEntity();
        if (requestMap.containsKey("categoryId")) {
             category.setId(Long.parseLong(requestMap.get("categoryId")));
             if (isAdd) {
                 product.setId(UUID.fromString(requestMap.get("id")));
                 product.setStatus(requestMap.get("status") == null ? "true" : requestMap.get("status"));
             } else {
                 product.setStatus("true");
             }
            product.setName(requestMap.get("name"));
            product.setCategory(category);
            product.setDescription(requestMap.get("description"));
            product.setPrice(Double.parseDouble(requestMap.get("price")));

        }
        return product;
    }
}
