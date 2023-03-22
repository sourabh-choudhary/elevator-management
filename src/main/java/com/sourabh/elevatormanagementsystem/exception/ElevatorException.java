package com.sourabh.elevatormanagementsystem.exception;

public final class ElevatorException extends RuntimeException {

	public ElevatorException() {
		super();
	}
	public ElevatorException(String message, Throwable cause) {
		super(message, cause);
	}
	public ElevatorException(String message) {
		super(message);
	}
	public ElevatorException(Throwable cause) {
		super(cause);
	}
}
