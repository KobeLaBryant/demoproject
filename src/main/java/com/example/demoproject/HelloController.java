package com.example.demoproject;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@RestController
public class HelloController {

    @Autowired
    private PersonRepository personRepository;


    //@RequestParam(value="name", defaultValue= "") String name
    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public @ResponseBody String addNewPerson(@RequestBody Person pIn){

        Person newPerson = new Person();
        newPerson.setName(pIn.getName());
        newPerson.setEmail(pIn.getEmail());


        for(Person p: personRepository.findAll())
        {
            if(p.getName()
                .equals(
                    newPerson.getName())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person already exists in DB");
            }


        }
        personRepository.save(newPerson);
        return newPerson.getName() + " saved";

    }

    @GetMapping("/getAll")
    public  @ResponseBody Iterable<Person> index(){
        return personRepository.findAll();
    }



}

