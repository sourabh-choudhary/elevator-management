package com.sourabh.elevatormanagementsystem.service;

import com.sourabh.elevatormanagementsystem.model.Elevator;

import java.io.IOException;
import java.util.List;

public interface ElevatorService {

    void save(Elevator elevator);
    List<Elevator> findAll();
    Elevator requestElevator(int startingFloor, int targetFloor) throws InterruptedException;
    Elevator findFreeElevator(int startingFloor);
    void startMoving(Elevator elevator) throws InterruptedException, IOException;
    int floorsCovered(Long elevatorId);


}
