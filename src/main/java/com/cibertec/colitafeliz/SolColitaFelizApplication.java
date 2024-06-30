package com.cibertec.colitafeliz;

import com.cibertec.colitafeliz.dao.CategoryDao;
import com.cibertec.colitafeliz.dao.ProductDao;
import com.cibertec.colitafeliz.dao.UserDao;
import com.cibertec.colitafeliz.entities.CategoryEntity;
import com.cibertec.colitafeliz.entities.ProductEntity;
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
    CommandLineRunner init(UserDao userDao, CategoryDao categoryDao, ProductDao productDao) {
        return args -> {
            saveUsers(userDao);
            saveCategories(categoryDao);
            saveProducts(productDao, categoryDao);
        };
    }

    private UserEntity createUser(String name, String contactNumber, String email, String password, String status, String role) {
        return UserEntity.builder()
                .name(name)
                .contactNumber(contactNumber)
                .email(email)
                .password(password)
                .status(status)
                .role(role)
                .build();
    }

    private void saveUsers(UserDao userDao) {
        List<UserEntity> users = List.of(
                createUser("Carlos Admin", "123456789", "carl.enc90@gmail.com", "password123", "true", "admin"),
                createUser("Jane Smith", "987654321", "jane.smith@example.com", "password456", "false", "user"),
                createUser("Michael Johnson", "5551234567", "michael.johnson@example.com", "pass123", "true", "user"),
                createUser("Emily Brown", "7779876543", "emily.brown@example.com", "pass456", "false", "user"),
                createUser("David Lee", "9998765432", "david.lee@example.com", "pass789", "true", "user"),
                createUser("Sophia Wilson", "1112345678", "sophia.wilson@example.com", "password789", "false", "user"),
                createUser("Oliver Davis", "2223456789", "oliver.davis@example.com", "passabc", "true", "user"),
                createUser("Isabella Martinez", "3334567890", "isabella.martinez@example.com", "passwordxyz", "false", "user")
        );
        userDao.saveAll(users);
    }

    private CategoryEntity createCategory(String name) {
        return CategoryEntity.builder()
                .name(name)
                .build();
    }

    private void saveCategories(CategoryDao categoryDao) {
        List<CategoryEntity> categories = List.of(
                createCategory("Comida para perros"),
                createCategory("Comida para gatos"),
                createCategory("Comida para peces"),
                createCategory("Comida para aves"),
                createCategory("Comida para roedores")
        );
        categoryDao.saveAll(categories);
    }

    private ProductEntity createProduct(String name, CategoryEntity category, String description, Double price, String status) {
        return ProductEntity.builder()
                .name(name)
                .category(category)
                .description(description)
                .price(price)
                .status(status)
                .build();
    }

    private void saveProducts(ProductDao productDao, CategoryDao categoryDao) {
        List<CategoryEntity> categories = categoryDao.findAll();

        CategoryEntity dogFoodCategory = categories.stream()
                .filter(category -> "Comida para perros".equalsIgnoreCase(category.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found: Comida para perros"));

        CategoryEntity catFoodCategory = categories.stream()
                .filter(category -> "Comida para gatos".equalsIgnoreCase(category.getName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found: Comida para gatos"));

        List<ProductEntity> products = List.of(
                createProduct("Dog Food A", dogFoodCategory, "High quality dog food", 20.99, "true"),
                createProduct("Dog Food B", dogFoodCategory, "Affordable dog food", 10.99, "true"),
                createProduct("Cat Food A", catFoodCategory, "Premium cat food", 15.99, "false"),
                createProduct("Cat Food B", catFoodCategory, "Economy cat food", 8.99, "true")
        );
        productDao.saveAll(products);
    }

}
