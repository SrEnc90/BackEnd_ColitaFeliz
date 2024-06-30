package com.cibertec.colitafeliz.service;

import com.cibertec.colitafeliz.entities.CategoryEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    public ResponseEntity<List<CategoryEntity>> getAllCategories(String filterValue);
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap);
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap);
}
