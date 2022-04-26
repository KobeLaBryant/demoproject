package com.example.demoproject;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;


@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository personRepository;


    @Test
    public void getAllTest() throws Exception{
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/getAll")
                                .accept(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();

        Person[] persons;
        ObjectMapper mapper = new ObjectMapper();
        String resp_string = result.getResponse().getContentAsString();
        List<Person> participantJsonList = mapper.readValue(resp_string, new TypeReference<List<Person>>(){});


    }

    @Test
    public void addTest() throws Exception{

        String testName = "TestName";
        String testEmail = "Test.Email@krone.de";

        Object randObj = new Person(){{
            setName(testName);
            setEmail(testEmail);
        }};


        //Add new Test-Person to DB and check for correct Response

        ObjectMapper objMapper = new ObjectMapper();
        String json = objMapper.writeValueAsString(randObj);

        mvc.perform(MockMvcRequestBuilders.post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .characterEncoding("utf-8"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(equalTo(String.format("%s saved", testName))));

        //Remove the Test Person from DB
        Person newDBperson = StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .filter(x -> x.getName().equals(testName))
                .findFirst()
                .orElseThrow();

        personRepository.deleteById(newDBperson.getId());


    }

}
