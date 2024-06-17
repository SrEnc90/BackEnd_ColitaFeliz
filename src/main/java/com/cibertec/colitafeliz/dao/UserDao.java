package com.cibertec.colitafeliz.dao;

import com.cibertec.colitafeliz.entities.UserEntity;
import com.cibertec.colitafeliz.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<UserEntity, UUID>{

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findByEmailId(@Param("email") String email);

    @Query("SELECT new com.cibertec.colitafeliz.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) FROM UserEntity u WHERE u.role = 'user'")
    List<UserWrapper> getAllUsers();

    @Query("SELECT u.email FROM UserEntity u WHERE u.role = 'admin'")
    List<String> getAllAdmin();

    @Modifying
    @Transactional // solo se utiliza con update y delete(cuándo va a ver un cambio de estado)
    @Query("UPDATE UserEntity u SET u.status = :status WHERE u.id = :id")
    Integer updateStatus(@Param("status") String status, @Param("id") UUID id);



    /*
    * Ejemplo de cuándo sería necesario el uso del atributo Param
    *   @Query("SELECT u FROM UserEntity u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    *   List<UserEntity> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
    * La anotación @Param se usa para mapear los parámetros del método findByFirstNameAndLastName (firstName y lastName) a los parámetros de la consulta JPQL (:firstName y :lastName).
    * */
}
