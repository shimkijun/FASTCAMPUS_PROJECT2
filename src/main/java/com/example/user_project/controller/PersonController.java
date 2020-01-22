package com.example.user_project.controller;

import com.example.user_project.controller.dto.PersonDto;
import com.example.user_project.domain.Person;
import com.example.user_project.domain.exception.PersonNotFoundException;
import com.example.user_project.domain.exception.RenameNotPermittedException;
import com.example.user_project.domain.exception.dto.ErrorResponse;
import com.example.user_project.repository.PersonRepository;
import com.example.user_project.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody PersonDto personDto){

        personService.put(personDto);
    }

    @PutMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, @RequestBody PersonDto person){
            personService.modify(id,person);
    }

    @PatchMapping("/{id}")
    public void modifyPerson(@PathVariable Long id, String name){
        personService.modify(id,name);;
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id){
        personService.delete(id);
        //return personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(id));
    }



}
