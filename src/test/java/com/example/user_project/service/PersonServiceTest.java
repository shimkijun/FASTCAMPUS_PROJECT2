package com.example.user_project.service;

import com.example.user_project.domain.Block;
import com.example.user_project.domain.Person;
import com.example.user_project.repository.BlockRepository;
import com.example.user_project.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BlockRepository blockRepository;

    @Test
    void getPeopleExcludeBlocks() {
        givenPeople();

        List<Person> result = personService.getPeopleExcludeBlocks();
         result.forEach(System.out::println);
    }

    @Test
    void getPeopleByName(){
        givenPeople();
        List<Person> result = personService.getPeopleByName("martin");
        result.forEach(System.out::println);
    }

    @Test
    void cascadeTest(){
        givenPeople();

        List<Person> result = personRepository.findAll();
        result.forEach(System.out::println);

        Person person = result.get(3);
        person.getBlock().setStartDate(LocalDate.now());
        person.getBlock().setEndDate(LocalDate.now());

        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);

//        personRepository.delete(person);
//        personRepository.findAll().forEach(System.out::println);
//        blockRepository.findAll().forEach(System.out::println);
        person.setBlock(null);
        personRepository.save(person);
        personRepository.findAll().forEach(System.out::println);
        blockRepository.findAll().forEach(System.out::println);

    }

    @Test
    void getPerson(){
        givenPeople();
        Person person = personService.getPerson(3L);
        System.out.println(person);
    }

    @Test
    void findByBloodType(){
        givenPerson("martin",10,"A");
        givenPerson("david",9,"B");
        givenPerson("denis",7,"O");
        givenPerson("martin",11,"AB");

        List<Person> result = personRepository.findByBloodType("A");

        result.forEach(System.out::println);
    }

    private void givenBlockPerson(String name, int age, String bloodType){
        Person blockPerson = new Person(name,age,bloodType);
        blockPerson.setBlock(new Block(name));

        personRepository.save(blockPerson);
    }
    private void givenPeople() {
        givenPerson("martin",10,"A");
        givenPerson("david",9,"B");
        givenPerson("denis",7,"O");
        givenBlockPerson("martin",11,"AB");
    }

    private void givenPerson(String name, int age, String bloodType) {
        personRepository.save(new Person(name,age,bloodType));
    }
}