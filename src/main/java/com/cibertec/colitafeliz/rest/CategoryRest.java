package com.cibertec.colitafeliz.rest;

import com.cibertec.colitafeliz.entities.CategoryEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/category")
public interface CategoryRest {

    @GetMapping(path = "/get")
    ResponseEntity<List<CategoryEntity>> getAllCategories(@RequestParam(required = false) String filterValue);

    @PostMapping(path = "/add")
    ResponseEntity<String> addNewCategory(@RequestBody(required = true)Map<String, String> requestMap);

    @PostMapping(path = "/update")
    ResponseEntity<String> updateCategory(@RequestBody(required = true)Map<String, String> requestMap);

}
