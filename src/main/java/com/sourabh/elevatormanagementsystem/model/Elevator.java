package com.sourabh.elevatormanagementsystem.model;

import jakarta.persistence.*;

@Entity
public class Elevator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "elevator_id")
    private Long id;
    @Column(name = "current_floor")
    private int floor=0;
    @Column(name = "direction")
    private EDirection direction = EDirection.STOP;
    private int targetFloor;
    @Column(name = "state")
    private EState state = EState.IDLE;

    @Column(name="floors_covered")
    private int no_floors_covered=0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id",nullable = false)
    private Hotel hotel;

    public Elevator() {
    }

    public Elevator(Long id, int floor, EDirection direction, int targetFloor, EState state) {
        this.id = id;
        this.floor = floor;
        this.direction = direction;
        this.targetFloor = targetFloor;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setDirection(EDirection direction) {
        this.direction = direction;
    }

    public void setTargetFloor(int targetFloor) {
        this.targetFloor = targetFloor;
    }

    public void setState(EState state) {
        this.state = state;
    }

    public int getFloor() {
        return floor;
    }

    public EDirection getDirection() {
        return direction;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public EState getState() {
        return state;
    }


    public int getNo_floors_covered() {
        return no_floors_covered;
    }

    public void setNo_floors_covered(int no_floors_covered) {
        this.no_floors_covered = no_floors_covered;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
