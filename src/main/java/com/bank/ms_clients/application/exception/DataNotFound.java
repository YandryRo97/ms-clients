package com.bank.ms_clients.application.exception;

import jakarta.persistence.EntityNotFoundException;

public class DataNotFound extends EntityNotFoundException {
    public DataNotFound(String messageString){
        super(messageString);
    }

}
