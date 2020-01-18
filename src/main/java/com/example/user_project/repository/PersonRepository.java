package com.example.user_project.repository;

import com.example.user_project.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person,Long> {
}
