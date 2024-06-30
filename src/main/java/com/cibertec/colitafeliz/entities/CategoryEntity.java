package com.cibertec.colitafeliz.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"products"})
@ToString(exclude = {"products"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(targetEntity = ProductEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
    @JsonManagedReference
    private List<ProductEntity> products;
}
