package com.example.user_project.repository;

import com.example.user_project.domain.Person;
import com.example.user_project.domain.dto.Birthday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    void crud(){
        Person person = new Person();
        person.setName("martin");
        person.setAge(10);
        person.setBloodType("A");
        personRepository.save(person);

        //System.out.println(personRepository.findAll());
        List<Person> people = personRepository.findAll();
        Assertions.assertEquals(people.size(),1);
        Assertions.assertEquals(people.get(0).getName(),"martin");
        Assertions.assertEquals(people.get(0).getAge(),10);
        Assertions.assertEquals(people.get(0).getBloodType(),"A");
    }

    @Test
    void findByBirthDayBetween(){
        givenPerson("martin",10,"A",LocalDate.of(1991,8,15));
        givenPerson("david",9,"B",LocalDate.of(1992,7,10));
        givenPerson("denis",7,"O",LocalDate.of(1993,6,15));
        givenPerson("martin",11,"AB",LocalDate.of(1994,1,15));

        List<Person> result = personRepository.findByMonthOfBirthday(8);
        result.forEach(System.out::println);
    }

    @Test
    void hashCodeAndEquals(){
        Person person1 = new Person("martin",10,"A");
        Person person2 = new Person("martin",10,"B");

        System.out.println(person1.equals(person2));
        System.out.println(person1.hashCode());
        System.out.println(person1.hashCode());
        Map<Person,Integer> map = new HashMap<>();
        map.put(person1, person1.getAge());
        System.out.println(map);
        System.out.println(map.get(person2));
    }

    private void givenPerson(String name, int age, String bloodType,LocalDate birthday) {
        Person person = new Person(name,age,bloodType);
        person.setBirthday(new Birthday(birthday));
        personRepository.save(person);
    }
}