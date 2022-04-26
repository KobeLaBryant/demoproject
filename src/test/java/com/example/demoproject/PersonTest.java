package com.example.demoproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonTest {

    @Test
    public void TestMethod(){
        Person eike = new Person();
        eike.setName("Eike");

        assertTrue(eike.getName() == "Eike");
    }

}
