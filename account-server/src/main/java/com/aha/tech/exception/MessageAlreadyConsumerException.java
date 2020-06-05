package com.aha.tech.exception;

/**
 * @author: ahaschool
 * @date: 2019-05-23 10:41
 */

public class MessageAlreadyConsumerException extends RuntimeException {
    public MessageAlreadyConsumerException() {
    }

    public MessageAlreadyConsumerException(String message) {
        super(message);
    }
}
