package com.TouristNest.travelGuide.Config;

import com.TouristNest.travelGuide.JPArepository.UserDataRepository;
import com.TouristNest.travelGuide.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDataRepository userDataRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDataRepository.findByEmail(email);
        if (Objects.equals(user, null)) {
            throw new UsernameNotFoundException("Username not found!");
        } else {
            return new CustomUser(user);
        }
    }

}
