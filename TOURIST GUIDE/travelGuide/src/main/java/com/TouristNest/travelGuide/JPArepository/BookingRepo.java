package com.TouristNestApplication.TravelGuide.JPArepository;

import com.TouristNestApplication.TravelGuide.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
