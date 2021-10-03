package com.godel.simplecrud.exceptions;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private LocalDateTime whenHappen;
    private String message;
    private String description;
}
