package com.cibertec.colitafeliz.rest;

import com.cibertec.colitafeliz.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/sign-up")
    public ResponseEntity<String> singUp(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/log-in")
    public ResponseEntity<String> logIn(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUsers();

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateUser(@RequestBody(required = true) Map<String, String> requestMap);

    @GetMapping(path = "/check-token")
    public ResponseEntity<String> checkToken();

    @PostMapping(path = "/change-password")
    public ResponseEntity<String> changePassword(@RequestBody(required = true) Map<String, String> requestMap);

    @PostMapping(path = "/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody(required = true) Map<String, String> requestMap);
}
