package com.example.user_project.service;

import com.example.user_project.controller.dto.PersonDto;
import com.example.user_project.domain.Person;
import com.example.user_project.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;

    @Test
    void getPeopleByName(){
        when(personRepository.findByName("martin"))
                .thenReturn(Lists.newArrayList(new Person("martin")));

        List<Person> result = personService.getPeopleByName("martin");

        assertEquals(result.size(),1);
        assertEquals(result.get(0).getName(),"martin");
    }

    @Test
    void getPerson(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("martin")));

        Person person = personService.getPerson(1L);

        assertEquals(person.getName(),"martin");
    }

    @Test
    void getPersonIfNotFound(){ 
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person person = personService.getPerson(1L);
        assertNull(person);
    }

    @Test
    void put(){
        PersonDto dto = PersonDto.of("martin","programming","판교", LocalDate.now(),"programmer","010-1111-1111");

        personService.put(dto);
        verify(personRepository,times(1)).save(any(Person.class));
    }

}