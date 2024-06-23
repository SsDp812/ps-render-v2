package com.sar.psapp.dto.request;


import com.sar.psapp.commons.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {
    private String userName;
    private String email;
    private String password;
    private UserRole role;
}
