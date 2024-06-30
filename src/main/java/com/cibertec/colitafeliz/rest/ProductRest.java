package com.cibertec.colitafeliz.rest;

import com.cibertec.colitafeliz.wrapper.ProductWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/product")
public interface ProductRest {

    @PostMapping(path = "/add")
    public ResponseEntity<String> addNewProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<ProductWrapper>> getAllProducts();

    @PostMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id);

    @PostMapping("/update-status")
    public ResponseEntity<String> updateStatus(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping("/get-by-category/{id}")
    public ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Long id);

    @GetMapping("/get-by-id/{id}")
    ResponseEntity<ProductWrapper> getProductById(@PathVariable String id);
}
