package com.petsAdoption.oauth.service;


import com.petsAdoption.oauth.util.AuthToken;

public interface AuthService {
    public AuthToken login(String username, String password, String clientId , String ClientSecret);
}
