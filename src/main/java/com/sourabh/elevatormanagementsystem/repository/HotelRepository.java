package com.sourabh.elevatormanagementsystem.repository;

import com.sourabh.elevatormanagementsystem.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Long> {
}
