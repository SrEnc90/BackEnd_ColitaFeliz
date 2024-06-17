package com.cibertec.colitafeliz;

import com.cibertec.colitafeliz.dao.UserDao;
import com.cibertec.colitafeliz.entities.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SolColitaFelizApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolColitaFelizApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserDao userDao) {
        return args -> {

            UserEntity user1 = UserEntity.builder()
                    .name("Carlos Admin")
                    .contactNumber("123456789")
                    .email("carl.enc90@gmail.com")
                    .password("password123")
                    .status("true")
                    .role("admin")
                    .build();

            UserEntity user2 = UserEntity.builder()
                    .name("Jane Smith")
                    .contactNumber("987654321")
                    .email("jane.smith@example.com")
                    .password("password456")
                    .status("false")
                    .role("user")
                    .build();

            UserEntity user3 = UserEntity.builder()
                    .name("Michael Johnson")
                    .contactNumber("5551234567")
                    .email("michael.johnson@example.com")
                    .password("pass123")
                    .status("true")
                    .role("user")
                    .build();

            UserEntity user4 = UserEntity.builder()
                    .name("Emily Brown")
                    .contactNumber("7779876543")
                    .email("emily.brown@example.com")
                    .password("pass456")
                    .status("false")
                    .role("user")
                    .build();

            UserEntity user5 = UserEntity.builder()
                    .name("David Lee")
                    .contactNumber("9998765432")
                    .email("david.lee@example.com")
                    .password("pass789")
                    .status("true")
                    .role("user")
                    .build();

            UserEntity user6 = UserEntity.builder()
                    .name("Sophia Wilson")
                    .contactNumber("1112345678")
                    .email("sophia.wilson@example.com")
                    .password("password789")
                    .status("false")
                    .role("user")
                    .build();

            UserEntity user7 = UserEntity.builder()
                    .name("Oliver Davis")
                    .contactNumber("2223456789")
                    .email("oliver.davis@example.com")
                    .password("passabc")
                    .status("true")
                    .role("user")
                    .build();

            UserEntity user8 = UserEntity.builder()
                    .name("Isabella Martinez")
                    .contactNumber("3334567890")
                    .email("isabella.martinez@example.com")
                    .password("passwordxyz")
                    .status("false")
                    .role("user")
                    .build();

            userDao.saveAll(List.of(user1, user2, user3, user4, user5, user6, user7, user8));
        };

    }

}
