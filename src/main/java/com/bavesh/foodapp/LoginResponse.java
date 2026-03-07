package com.bavesh.foodapp;

public class LoginResponse {
    private String token;
    private String email;
    private String type = "Bearer";

    public LoginResponse(String token, String email) {
        this.token = token;
        this.email = email;
    }

    public String getToken() { return token; }
    public String getEmail() { return email; }
    public String getType() { return type; }
}
