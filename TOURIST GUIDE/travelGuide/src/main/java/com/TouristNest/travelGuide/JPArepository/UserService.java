package com.TouristNest.travelGuide.JPArepository;

import com.TouristNest.travelGuide.Model.User;

public interface UserService {

    public User createUser(User user);
    boolean existsByEmail(String email);
}
