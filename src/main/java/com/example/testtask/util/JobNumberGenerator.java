package com.example.testtask.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@Component
public class JobNumberGenerator {

    private final AtomicLong number;

    public JobNumberGenerator() {
        number = new AtomicLong();
    }

    public Long generate() {
        return number.getAndIncrement();
    }
}
