package com.hcl.mortgagechecker.exception;

/**
 * Custom exception for when the interest rate for a given maturity period is not found
 * @author: Jaspreet.Singh
 */
public class InterestRateNotFoundException extends RuntimeException{
    public InterestRateNotFoundException(String message) {
        super(message);
    }
}
