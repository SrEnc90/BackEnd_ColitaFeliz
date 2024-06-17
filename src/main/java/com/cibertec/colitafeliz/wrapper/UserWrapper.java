package com.cibertec.colitafeliz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWrapper {

    private UUID id;
    private String name;
    private String email;
    private String contactNumber;
    private String status;

}
