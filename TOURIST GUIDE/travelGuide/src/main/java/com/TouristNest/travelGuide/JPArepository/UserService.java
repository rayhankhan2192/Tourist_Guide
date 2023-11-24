package com.TouristNestApplication.TravelGuide.JPArepository;

import com.TouristNestApplication.TravelGuide.Model.User;

import java.util.Date;

public interface UserService {

    public User createUser(User user);
    boolean existsByEmail(String email);
    public String sendOTPToUser(String userEmail, String name);
}

