package com.racetalk.service;

import com.racetalk.entity.User;

import java.util.Optional;

public interface UserService {
    public void registerUser(String username, String password);

    public Optional<User> loginUser(String username, String password);

    public Optional<User> findById(int id);
}
