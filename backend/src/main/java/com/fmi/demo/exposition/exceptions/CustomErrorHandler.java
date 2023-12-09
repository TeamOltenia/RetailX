package com.fmi.demo.exposition.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomErrorHandler extends RuntimeException{

    private static final long serialVersionUID = 1234567L;

    private final ErrorDispatcher errorDispatcher;
}