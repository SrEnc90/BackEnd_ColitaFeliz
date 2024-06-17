package com.cibertec.colitafeliz.rest.implement;

import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.dao.UserDao;
import com.cibertec.colitafeliz.rest.UserRest;
import com.cibertec.colitafeliz.service.UserService;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import com.cibertec.colitafeliz.wrapper.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        try {
            return userService.singUp(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> logIn(Map<String, String> requestMap) {
        try {
            return userService.logIn(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            return userService.updateUser(requestMap);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
