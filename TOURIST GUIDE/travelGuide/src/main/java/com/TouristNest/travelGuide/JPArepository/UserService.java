package com.TouristNest.travelGuide.JPArepository;

import com.TouristNest.travelGuide.Model.User;

// import java.util.Date;

public interface UserService {

    public User createUser(User user);
    boolean existsByEmail(String email);
    public String sendOTPToUser(String userEmail, String name);
}

