package com.cibertec.colitafeliz.rest.implement;

import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.entities.CategoryEntity;
import com.cibertec.colitafeliz.rest.CategoryRest;
import com.cibertec.colitafeliz.service.CategoryService;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CategoryRestImpl implements CategoryRest {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryEntity>> getAllCategories(String filterValue) {
        try {
            return categoryService.getAllCategories(filterValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            return categoryService.addNewCategory(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            return categoryService.updateCategory(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
