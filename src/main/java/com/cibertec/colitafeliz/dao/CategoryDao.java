package com.cibertec.colitafeliz.dao;

import com.cibertec.colitafeliz.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity, Long> {

    @Query("SELECT c FROM CategoryEntity c WHERE c.id in (SELECT p.category.id FROM ProductEntity p WHERE p.status = 'true')")
    List<CategoryEntity> getAllCategories();
}
