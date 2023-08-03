package com.example.AngularSpring.Auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String uname;

    private String uemail;

    private  String upassword;
}
