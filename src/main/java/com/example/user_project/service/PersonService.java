package com.example.user_project.service;

import com.example.user_project.controller.dto.PersonDto;
import com.example.user_project.domain.Person;
import com.example.user_project.domain.exception.PersonNotFoundException;
import com.example.user_project.domain.exception.RenameNotPermittedException;
import com.example.user_project.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Transactional(readOnly = true)
    public Person getPerson(Long id){
//        Person person = personRepository.findById(id).get();
        return personRepository.findById(id).orElse(null);
    }

    public List<Person> getPeopleByName(String name) {
//        List<Person> people = personRepository.findAll();
//        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList());
        return personRepository.findByName(name);
    }

    @Transactional
    public void put(PersonDto personDto){
        Person person = new Person();
        person.set(personDto);
        person.setName(personDto.getName());
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id, PersonDto personDto) {
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);

        if(!person.getName().equals(personDto.getName())){
            throw  new RenameNotPermittedException();
        }
        person.set(personDto);
        personRepository.save(person);
    }

    @Transactional
    public void modify(Long id,String name){
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setName(name);

        personRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        //personRepository.deleteById(id);
        Person person = personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
        person.setDeleted(true);
        personRepository.save(person);
    }
}
