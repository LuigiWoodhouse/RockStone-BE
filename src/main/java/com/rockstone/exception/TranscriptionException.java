package com.rockstone.exception;

import lombok.Data;

@Data
public class TranscriptionException extends Exception {

    private static final long serialVersionUID = 1L;

    private int code;

    public TranscriptionException(int code, String message) {
        super(message);
        this.code = code;
    }
}