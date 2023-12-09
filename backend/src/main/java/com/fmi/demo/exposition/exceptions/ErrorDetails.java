package com.fmi.demo.exposition.exceptions;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private ZonedDateTime errorDate;
    private String detail;
    private String developerMessage;
    private String title;
    private String errorKey;
    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
