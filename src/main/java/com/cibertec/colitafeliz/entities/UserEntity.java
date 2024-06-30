package com.cibertec.colitafeliz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.UUID;

/* Estoy mapeando en el archivo UserDao.java
@NamedQueries({
        @NamedQuery(name = "UserEntity.findByEmailId", query = "SELECT u FROM UserEntity u WHERE u.email = :email"),
        @NamedQuery(name = "UserEntity.getAllUsers", query = "SELECT new com.cibertec.colitafeliz.wrapper.UserWrapper(u.id, u.name, u.email, u.contactNumber, u.status) FROM UserEntity u WHERE u.role = 'user'"),
        @NamedQuery(name = "UserEntity.updateStatus", query = "UPDATE UserEntity u SET u.status = :status WHERE u.id = :id")
})
*/
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    private String status;

    private String role;
}
