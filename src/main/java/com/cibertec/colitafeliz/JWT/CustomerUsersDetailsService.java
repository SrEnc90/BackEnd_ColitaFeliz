package com.cibertec.colitafeliz.JWT;

import com.cibertec.colitafeliz.dao.UserDao;
import com.cibertec.colitafeliz.entities.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    private Optional<UserEntity> userEntity;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername {} ", email);
        userEntity = userDao.findByEmailId(email);
        if (userEntity.isPresent()) {
            return new User(userEntity.get().getEmail(), userEntity.get().getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public Optional<UserEntity> getUserEntity() {
        Optional<UserEntity> user = userEntity;
        user.get().setPassword(null);
        return user;
    }

}
