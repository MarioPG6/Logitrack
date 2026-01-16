package com.mariopg.logitrack.auth;


import com.mariopg.logitrack.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String email;
    String password;
    String firstname;
    String lastname;
    String direction;
    String phone;
    Role role;
}
