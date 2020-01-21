package com.example.user_project.service;

import com.example.user_project.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;


    @Test
    void getPeopleByName(){
        List<Person> result = personService.getPeopleByName("martin");
        assertEquals(result.size(),1);
        assertEquals(result.get(0).getName(),"martin");
    }

    @Test
    void getPerson(){
        Person person = personService.getPerson(3L);
        assertEquals(person.getName(),"dennis");
    }

}