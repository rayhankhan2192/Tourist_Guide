package com.TouristNestApplication.TravelGuide.JPArepository;

import com.TouristNestApplication.TravelGuide.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}

