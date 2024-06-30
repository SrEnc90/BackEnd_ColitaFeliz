package com.cibertec.colitafeliz.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@DynamicInsert
@Entity
@Table(name = "bill")
public class BillEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @Column(name = "razon_social")
    private String razonSocial;

    @Email
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    private String address;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "product_details", columnDefinition = "json")
    private String productDetails;

    @Column(name = "created_by")
    private String createdBy;

}
