package com.myonlineshopping.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthResponse {
    private String email;
    private String accessToken;
}