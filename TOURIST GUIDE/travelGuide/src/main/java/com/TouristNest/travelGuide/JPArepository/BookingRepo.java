package com.TouristNest.travelGuide.JPArepository;

import com.TouristNest.travelGuide.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
