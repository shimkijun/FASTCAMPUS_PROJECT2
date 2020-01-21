package com.example.user_project.repository;

import com.example.user_project.domain.Person;
import com.example.user_project.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("john");
        personRepository.save(person);
        List<Person> result = personRepository.findByName("john");
        assertEquals(result.size(),1);
        assertEquals(result.get(0).getName(),"john");
//        assertEquals(result.get(0).getAge(),10);
    }

    @Test
    void findByBirthDayBetween(){
        List<Person> result = personRepository.findByMonthOfBirthday(8);
        assertEquals(result.size(),2);
        assertEquals(result.get(0).getName(),"martin");
        assertEquals(result.get(1).getName(),"sophia");
    }

}