package com.example.user_project.domain;

import com.example.user_project.controller.dto.PersonDto;
import com.example.user_project.domain.dto.Birthday;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Where(clause = "deleted = false")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotEmpty
    @Column(nullable = false)
    private String name;

    private String hobby;

    private String address;

    @Valid
    @Embedded
    private Birthday birthday;

    private String job;

    private String phoneNumber;

    @ColumnDefault("0")
    private boolean deleted;

    public void set(PersonDto personDto){
       if(!StringUtils.isEmpty(personDto.getHobby())){
           this.setHobby(personDto.getHobby());
       }

       if(!StringUtils.isEmpty(personDto.getAddress())){
           this.setAddress(personDto.getAddress());
       }

       if(!StringUtils.isEmpty(personDto.getJob())){
           this.setJob(personDto.getJob());
       }

       if(!StringUtils.isEmpty(personDto.getPhoneNumber())){
           this.setPhoneNumber(personDto.getPhoneNumber());
       }

       if(personDto.getBirthday() != null){
           this.setBirthday(Birthday.of(personDto.getBirthday()));
       }
    }

    public Integer getAge(){
        if(this.birthday != null) {
            return LocalDate.now().getYear() - this.birthday.getYearOfBirthday() + 1;
        }else{
            return null;
        }
    }

    public boolean isBirthdayToday(){
        return LocalDate.now().equals(LocalDate.of(this.birthday.getYearOfBirthday(),this.birthday.getMonthOfBirthday(),this.birthday.getDayOfBirthday()));
    }

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
