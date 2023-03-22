package com.sourabh.elevatormanagementsystem.repository;

import com.sourabh.elevatormanagementsystem.model.Elevator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElevatorRepository extends JpaRepository<Elevator,Long> {

    public List<Elevator> findByHotelName(String name);
}
