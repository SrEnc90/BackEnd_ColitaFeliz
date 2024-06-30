package com.cibertec.colitafeliz.service.implement;

import com.cibertec.colitafeliz.JWT.JwtFilter;
import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.dao.CategoryDao;
import com.cibertec.colitafeliz.entities.CategoryEntity;
import com.cibertec.colitafeliz.service.CategoryService;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Override
    public ResponseEntity<List<CategoryEntity>> getAllCategories(String filterValue) {
        try {
            if (!Strings.isNullOrEmpty(filterValue) && filterValue.equalsIgnoreCase("true")) {
                log.info("Getting all filtered categories");
                return new ResponseEntity<List<CategoryEntity>>(categoryDao.getAllCategories(), HttpStatus.OK);
            }
            return new ResponseEntity<List<CategoryEntity>>(categoryDao.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<List<CategoryEntity>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> addNewCategory(Map<String, String> requestMap) {
        try {
            if(JwtFilter.isAdmin()) {
                if(validateCategoryMap(requestMap, false)) {
                    categoryDao.save(getCategoryFromMap(requestMap, false));
                    return GlobalUtils.getResponseEntity(GlobalConstants.SUCCESS_CREATED, HttpStatus.OK);
                } else {
                    return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if(JwtFilter.isAdmin()) {
                if(validateCategoryMap(requestMap, true)) {
                    Optional<CategoryEntity> category = categoryDao.findById(Long.parseLong(requestMap.get("id")));
                    if (category.isPresent()) {
                        categoryDao.save(getCategoryFromMap(requestMap, true));
                        return GlobalUtils.getResponseEntity(GlobalConstants.CATEGORY_UPDATED, HttpStatus.OK);
                    }
                    return GlobalUtils.getResponseEntity(GlobalConstants.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND);
                }
                return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            } else {
                return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateCategoryMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")) {
            if(requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private CategoryEntity getCategoryFromMap(Map<String, String> requestMap, boolean isAdd) {
        CategoryEntity category = new CategoryEntity();
        if (isAdd) {
            category.setId(Long.parseLong(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }

}
