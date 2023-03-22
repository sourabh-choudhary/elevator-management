package com.sourabh.elevatormanagementsystem.controller;

import com.sourabh.elevatormanagementsystem.model.Elevator;
import com.sourabh.elevatormanagementsystem.service.ElevatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elevator")
public class ElevatorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ElevatorService elevatorService;

    @GetMapping("/findAll")
    public List<Elevator> getElevators() {
        return elevatorService.findAll();
    }

    @PostMapping("/save")
    public void saveElevator(@RequestBody Elevator elevator) {
        elevatorService.save(elevator);
    }

    @GetMapping("/requestElevator/{startingFloor}&{targetFloor}")
    public Elevator requestElevator(@PathVariable int startingFloor,
                                    @PathVariable int targetFloor) throws InterruptedException {
        String logInfo = String.format("Request elevator for floorNo:%s, toFloorNo:%s",startingFloor, targetFloor);
        logger.debug(logInfo);
        return elevatorService.requestElevator(startingFloor, targetFloor);
    }

    @GetMapping("floorsCovered/{elevatorId}")
    public int getFloorsCoveredByElevator(@PathVariable Long elevatorId){
        String logInfo = String.format("Floors Covered by elevator:%s",elevatorId);
        logger.debug(logInfo);
        return elevatorService.floorsCovered(elevatorId);
    }
}
