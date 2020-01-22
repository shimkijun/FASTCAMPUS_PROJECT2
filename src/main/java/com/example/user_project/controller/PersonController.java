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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Page<Person> getAll(@PageableDefault Pageable pageable){
        return personService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable Long id){
        return personService.getPerson(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postPerson(@RequestBody @Valid PersonDto personDto){

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
