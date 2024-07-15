package com.crud_tutospring.demo.modelEntity;

import lombok.*;

import java.time.LocalDateTime;


@Data
public class ApiError {

    private String message;
    private int code;
    private LocalDateTime timestamp;


}


