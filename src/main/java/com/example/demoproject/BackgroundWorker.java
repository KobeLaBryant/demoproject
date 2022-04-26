package com.example.demoproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BackgroundWorker implements Runnable {

    @Autowired
    PersonRepository personRepository;


    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                }

             catch (InterruptedException e) {
                System.out.println("Exception");
            }

        }
    }
}
