package org.example.Exceptions;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Long id) {
            super("Student id not found : " + id);
        }
}
