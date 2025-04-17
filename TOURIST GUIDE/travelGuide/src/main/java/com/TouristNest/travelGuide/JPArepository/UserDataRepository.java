package com.TouristNest.travelGuide.JPArepository;

import com.TouristNest.travelGuide.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}

