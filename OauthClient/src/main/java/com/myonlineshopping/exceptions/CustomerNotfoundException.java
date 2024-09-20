package com.myonlineshopping.exceptions;

public class CustomerNotfoundException extends GlobalException {
        public static final long serialVersionUID = 4L;

        public CustomerNotfoundException() {
        super("Customer not found");
    }

    public CustomerNotfoundException(Long customerId) {
        super("Customer with id: " + customerId + " not found");
    }

}
