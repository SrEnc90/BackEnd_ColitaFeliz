package com.cibertec.colitafeliz.service.implement;

import com.cibertec.colitafeliz.JWT.CustomerUsersDetailsService;
import com.cibertec.colitafeliz.JWT.JwtFilter;
import com.cibertec.colitafeliz.JWT.JwtUtils;
import com.cibertec.colitafeliz.constants.GlobalConstants;
import com.cibertec.colitafeliz.dao.UserDao;
import com.cibertec.colitafeliz.entities.UserEntity;
import com.cibertec.colitafeliz.service.UserService;
import com.cibertec.colitafeliz.utils.EmailUtils;
import com.cibertec.colitafeliz.utils.GlobalUtils;
import com.cibertec.colitafeliz.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerUsersDetailsService customerUsersDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> singUp(Map<String, String> requestMap) {
        log.info("inside singUp {} " + requestMap);
        try {
            if(validateSingUpRequest(requestMap)){
                Optional<UserEntity> user = userDao.findByEmailId(requestMap.get("email"));
                if(!user.isPresent()){
                    userDao.save(getUserFromMap(requestMap));
                    return GlobalUtils.getResponseEntity(GlobalConstants.USER_CREATED, HttpStatus.OK);
                } else {
                    return GlobalUtils.getResponseEntity(GlobalConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
                }
            }else {
                return GlobalUtils.getResponseEntity(GlobalConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateSingUpRequest(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("password") && requestMap.containsKey("email")) {
            return true;
        }
        return false;
    }

    private UserEntity getUserFromMap(Map<String, String> requestMap) {
        UserEntity user = UserEntity.builder()
                .name(requestMap.get("name"))
                .contactNumber(requestMap.get("contactNumber"))
                .email(requestMap.get("email"))
                .password(requestMap.get("password"))
                .status("true")
                .role(GlobalConstants.USER)
                //.role("admin")
                .build();
        return user;
    }
    @Override
    public ResponseEntity<String> logIn(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));

            if(authentication.isAuthenticated()) {
                if(customerUsersDetailsService.getUserEntity().get().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtils.generateToken(customerUsersDetailsService.getUserEntity().get().getEmail(),
                            customerUsersDetailsService.getUserEntity().get().getRole()) + "\"}", HttpStatus.OK);
                } else {
                    return GlobalUtils.getResponseEntity(GlobalConstants.WAIT_VERIFICATION, HttpStatus.BAD_REQUEST);
                }
            }
            
        } catch (Exception e) {
            log.error("{}", e.getMessage());
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.USER_NOT_VERIFIED, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUsers() {
        try {
            if(JwtFilter.isAdmin()) {
                return new ResponseEntity<List<UserWrapper>>(userDao.getAllUsers(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<List<UserWrapper>>( new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
            if (JwtFilter.isAdmin()) {
                UUID userId = UUID.fromString(requestMap.get("id"));
                Optional<UserEntity> user = userDao.findById(userId);
                if (user.isPresent()) {
                    userDao.updateStatus(requestMap.get("status"), userId);
                    sendMailToAllAdmins(requestMap.get("status"), user.get().getName(), userDao.getAllAdmin());
                    return GlobalUtils.getResponseEntity(GlobalConstants.USER_UPDATED, HttpStatus.OK);
                } else {
                    return GlobalUtils.getResponseEntity("El usuario no existe", HttpStatus.OK);
                }
            } else {
                return GlobalUtils.getResponseEntity(GlobalConstants.UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GlobalUtils.getResponseEntity(GlobalConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmins(String status, String user, List<String> allAdmins) {
        allAdmins.remove(JwtFilter.getCurrentUser());

        if (status != null && status.equalsIgnoreCase("true")) {
            try {
                emailUtils.sendSimpleMessage(JwtFilter.getCurrentUser(), "Usuario habilitado", "Usuario:- " + user + "\n fue habilitado por el administrador:-" + JwtFilter.getCurrentUser(), allAdmins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                emailUtils.sendSimpleMessage(JwtFilter.getCurrentUser(), "Usuario deshabilitado", "Usuario:- " + user + "\n fue deshabilitado por el administrador:-" + JwtFilter.getCurrentUser(), allAdmins);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
