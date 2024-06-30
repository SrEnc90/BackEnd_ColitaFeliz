package com.cibertec.colitafeliz.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.UUID;
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {"category"})
@ToString(exclude = {"category"})
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "product")
public class ProductEntity implements Serializable {

    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne(targetEntity = CategoryEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category", nullable = false)
    @JsonBackReference
    private CategoryEntity category;

    private String description;

    private Double price;

    private String status;
}
