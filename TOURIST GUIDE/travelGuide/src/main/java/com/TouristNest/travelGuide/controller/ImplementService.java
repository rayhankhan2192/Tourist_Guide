package com.TouristNest.travelGuide.controller;

import com.TouristNest.travelGuide.JPArepository.UserDataRepository;
import com.TouristNest.travelGuide.JPArepository.UserService;
import com.TouristNest.travelGuide.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImplementService implements UserService {
    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public User createUser(User user) {
        return userDataRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        boolean emailExists = userDataRepository.existsById(email);
        if (emailExists) {
            return true;
        }
        return false;
    }

}
