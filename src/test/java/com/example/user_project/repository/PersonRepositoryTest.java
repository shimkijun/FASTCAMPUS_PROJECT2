package com.example.user_project.repository;

import com.example.user_project.domain.Person;
import com.example.user_project.domain.dto.Birthday;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void findByName(){
        List<Person> people = personRepository.findByName("tony");
        assertEquals(people.size(),1);

        System.out.println(people.get(0));

        Person person = people.get(0);
        assertAll(
                () -> assertEquals(person.getName(),"tony"),
                () -> assertEquals(person.getHobby(),"reading"),
                () -> assertEquals(person.getAddress(),"seoul"),
                () -> assertEquals(person.getBirthday(), Birthday.of(LocalDate.of(1991,7,10))),
                () -> assertEquals(person.getJob(),"officer"),
                () -> assertEquals(person.getHobby(),"reading"),
                () -> assertEquals(person.getPhoneNumber(),"010-2222-5555"),
                () -> assertEquals(person.isDeleted(),false)
        );
    }

    @Test
    void findByNameIfDeleted(){
        List<Person> people = personRepository.findByName("andrew");
        assertEquals(people.size(),0);
    }

    @Test
    void findByMonthOfBirthday(){
        List<Person> people = personRepository.findByMonthOfBirthday(7);

        assertEquals(people.size(),2);
        assertAll(
                () -> assertEquals(people.get(0).getName(),"david"),
                () -> assertEquals(people.get(1).getName(),"tony")
        );

    }

    @Test
    void findPeopleDeleted(){
        List<Person> people = personRepository.findPeopleDeleted();

        assertEquals(people.size(),1);
        assertEquals(people.get(0).getName(),"andrew");
    }


}