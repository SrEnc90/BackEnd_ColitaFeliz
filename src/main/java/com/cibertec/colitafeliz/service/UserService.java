package com.cibertec.colitafeliz.service;

import com.cibertec.colitafeliz.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    public ResponseEntity<String> singUp(Map<String, String> requestMap);
    public ResponseEntity<String> logIn(Map<String, String> requestMap);
    public ResponseEntity<List<UserWrapper>> getAllUsers();
    public ResponseEntity<String> updateUser(Map<String, String> requestMap);
}
