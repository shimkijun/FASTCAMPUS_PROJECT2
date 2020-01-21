package com.example.user_project.service;

import com.example.user_project.domain.Block;
import com.example.user_project.domain.Person;
import com.example.user_project.repository.BlockRepository;
import com.example.user_project.repository.PersonRepository;
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
    void getPeopleExcludeBlocks() {
        List<Person> result = personService.getPeopleExcludeBlocks();
        result.forEach(System.out::println);
        assertEquals(result.size(),5);
        assertEquals(result.get(0).getName(),"martin");
        assertEquals(result.get(1).getName(),"martin2");
//        assertEquals(result.get(1).getName(),"david");
        assertEquals(result.get(2).getName(),"benny");
        assertEquals(result.get(3).getName(),"martin2");
        assertEquals(result.get(4).getName(),"john");
    }

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