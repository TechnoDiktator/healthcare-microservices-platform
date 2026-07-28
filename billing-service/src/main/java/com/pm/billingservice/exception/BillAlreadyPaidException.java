package com.pm.billingservice.exception;

public class BillAlreadyPaidException extends RuntimeException {

    public BillAlreadyPaidException(String message) {
        super(message);
    }
}