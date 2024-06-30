package com.cibertec.colitafeliz.service;

import com.cibertec.colitafeliz.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap);
    public ResponseEntity<List<ProductWrapper>> getAllProducts();
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap);
    public ResponseEntity<String> deleteProduct(@PathVariable String id);
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap);
    public ResponseEntity<List<ProductWrapper>> getByCategory(Long id);
    public ResponseEntity<ProductWrapper> getProductById(String id);
}
