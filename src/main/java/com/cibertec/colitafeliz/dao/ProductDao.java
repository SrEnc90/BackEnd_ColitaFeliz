package com.cibertec.colitafeliz.dao;

import com.cibertec.colitafeliz.entities.ProductEntity;
import com.cibertec.colitafeliz.wrapper.ProductWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, UUID> {
    @Query("SELECT new com.cibertec.colitafeliz.wrapper.ProductWrapper(p.id, p.name, p.description, p.price, p.status, p.category.id, p.category.name) FROM ProductEntity p")
    List<ProductWrapper> getAllProducts();

    @Modifying
    @Transactional
    @Query("UPDATE ProductEntity p SET p.status = :status WHERE p.id = :uuid")
    void updateProductStatus(@Param("uuid") UUID uuid, @Param("status") String status);

    @Query("SELECT new com.cibertec.colitafeliz.wrapper.ProductWrapper(p.id, p.name) FROM ProductEntity p WHERE p.category.id = :id AND p.status = 'true'")
    List<ProductWrapper> getProductByCategory(@Param("id") Long id);

    @Query("SELECT new com.cibertec.colitafeliz.wrapper.ProductWrapper(p.id, p.name, p.description, p.price) FROM ProductEntity p WHERE p.id = :uuid")
    ProductWrapper getProductById(@Param("uuid") UUID uuid);
}
