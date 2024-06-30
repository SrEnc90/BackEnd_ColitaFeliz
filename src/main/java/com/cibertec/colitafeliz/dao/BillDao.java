package com.cibertec.colitafeliz.dao;

import com.cibertec.colitafeliz.entities.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDao extends JpaRepository<BillEntity, Integer> {
}
