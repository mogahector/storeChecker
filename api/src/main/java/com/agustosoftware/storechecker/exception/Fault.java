package com.agustosoftware.storechecker.exception;

import lombok.Data;

@Data
public class Fault {

    private int code;

    private String message;
}
