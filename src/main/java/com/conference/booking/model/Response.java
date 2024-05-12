package com.conference.booking.model;

import com.conference.booking.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_EMPTY)
public class Response<T> {
    private T data;
    private ResponseStatus status;
    private String message;
    private String code;
    private String type;
    private boolean result;
}
