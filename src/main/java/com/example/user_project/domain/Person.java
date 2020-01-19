package com.example.user_project.domain;

import com.example.user_project.domain.dto.Birthday;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Integer age;

    private String hobby;

    @NonNull
    private String bloodType;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @ToString.Exclude
    private Block block;

    /*public boolean equals(Object object){
        if(object == null){
            return false;
        }

        Person person = (Person) object;

        if(!person.getName().equals(this.getName())){
            return false;
        }
        if(person.getAge() != this.getAge()){
            return false;
        }
        return true;
    }

    public int hashCode(){
        return (name + age).hashCode();
    }*/
}
