package com.sourabh.elevatormanagementsystem.service;

import com.sourabh.elevatormanagementsystem.exception.ElevatorException;
import com.sourabh.elevatormanagementsystem.model.EDirection;
import com.sourabh.elevatormanagementsystem.model.EState;
import com.sourabh.elevatormanagementsystem.model.Elevator;
import com.sourabh.elevatormanagementsystem.repository.ElevatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ElevatorServiceImpl implements ElevatorService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ElevatorRepository elevatorRepository;
    private final int MAX_FLOOR = 16;
    private final int MIN_FLOOR = 0;

    @Override
    public void save(Elevator elevator) {
            elevatorRepository.save(elevator);
    }

    @Override
    public List<Elevator> findAll() {
        return elevatorRepository.findAll();
    }

    public List<Elevator> findByHotelName(String name) {
        return elevatorRepository.findByHotelName(name);
    }
    @Override
    public Elevator requestElevator(int startingFloor, int targetFloor) {
        String logInfo = String.format("Requesting elevator for startingFloor:%s",startingFloor);
        logger.debug(logInfo);
        Elevator elevator = findFreeElevator(startingFloor);

        if (elevator == null) {
            throw new ElevatorException("No free elevator\nTry again later");
        }
        if (startingFloor > elevator.getHotel().getFloors() || startingFloor < 0 || targetFloor > elevator.getHotel().getFloors() || targetFloor < 0) {
            throw new IllegalArgumentException("Invalid floor");
        }
        Thread t1 = new Thread(() -> {
            try {
                if (elevator.getFloor() != startingFloor) {
                    elevator.setTargetFloor(startingFloor);
                    startMoving(elevator);
                }
                elevator.setTargetFloor(targetFloor);
                startMoving( elevator);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        return elevator;
    }

    @Override
    public Elevator findFreeElevator(int startingFloor) {
        String hotelName="chevron";
        String logInfo = String.format("Finding elevator for startingFloor:%s",startingFloor);
        logger.debug(logInfo);
        Elevator elevator = new Elevator();
        HashMap<Elevator, Integer> elevatorsDistance = new HashMap<>();
        for (Elevator e : findByHotelName(hotelName)) {
            if (e.getState() == EState.IDLE && e.getDirection() == EDirection.STOP) {
                int distance = Math.abs(e.getFloor() - startingFloor);
                elevatorsDistance.put(e, distance);
            }
        }
        if (elevatorsDistance.isEmpty())
            return null;

        elevator = Collections.min(elevatorsDistance.entrySet(), Map.Entry.comparingByValue()).getKey();
        return elevator;
    }

    @Override
    public void startMoving( Elevator elevator) throws InterruptedException, IOException {
        if (elevator.getTargetFloor() > elevator.getFloor()) {
            elevator.setDirection(EDirection.UP);
            while (elevator.getFloor() < elevator.getTargetFloor()) {
                move(elevator);
            }
        } else if (elevator.getTargetFloor() < elevator.getFloor()) {
            elevator.setDirection(EDirection.DOWN);
            while (elevator.getFloor() > elevator.getTargetFloor()) {
                move(elevator);
            }
        }
        elevator.setDirection(EDirection.STOP);
        elevator.setState(EState.IDLE);
        elevatorRepository.save(elevator);
    }

    @Override
    public int floorsCovered(Long elevatorId) {
        Optional<Elevator> elevator=elevatorRepository.findById(elevatorId);
        if(elevator.isEmpty())
            return 0;
        return elevator.get().getNo_floors_covered();
    }

    private void move(Elevator elevator) throws InterruptedException, IOException {
        elevator.setState(EState.MOVING);
        Thread.sleep(1000);
        if (elevator.getDirection() == EDirection.UP) {
            elevator.setFloor(elevator.getFloor() + 1);
            elevator.setNo_floors_covered(elevator.getNo_floors_covered()+1);
        } else if (elevator.getDirection() == EDirection.DOWN) {
            elevator.setFloor(elevator.getFloor() - 1);
            elevator.setNo_floors_covered(elevator.getNo_floors_covered()+1);
        }
    }
}
