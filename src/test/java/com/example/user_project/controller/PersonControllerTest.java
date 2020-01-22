package com.example.user_project.controller;

import com.example.user_project.controller.dto.PersonDto;
import com.example.user_project.domain.Person;
import com.example.user_project.domain.dto.Birthday;
import com.example.user_project.domain.exception.handler.GlobalExceptionHandler;
import com.example.user_project.repository.PersonRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
class PersonControllerTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PersonController personController;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    void beforeEach(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .addFilter(((request, response, chain) -> {
                    request.setCharacterEncoding("UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request,response);
                }))
                .alwaysDo(print())
                .build();
    }

    @Test
    void getPerson() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/person/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("martin"))
                .andExpect(jsonPath("$.hobby").isEmpty())
                .andExpect(jsonPath("$.address").isEmpty())
                .andExpect(jsonPath("$.birthday").value("1991-08-15"))
                .andExpect(jsonPath("$.job").isEmpty())
                .andExpect(jsonPath("$.phoneNumber").isEmpty())
                .andExpect(jsonPath("$.deleted").value(false))
                .andExpect(jsonPath("$.age").isNumber())
                .andExpect(jsonPath("$.birthdayToday").isBoolean());
    }

    @Test
    void postPerson() throws Exception {

        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto)))
                .andExpect(status().isCreated());
        Person result = personRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).get(0);

        assertAll(
                () -> assertEquals(result.getName(),"martin"),
                () -> assertEquals(result.getHobby(),"programming"),
                () -> assertEquals(result.getAddress(),"판교"),
                () -> assertEquals(result.getBirthday(), Birthday.of(LocalDate.now())),
                () -> assertEquals(result.getJob(),"programmer" ),
                () -> assertEquals(result.getPhoneNumber(),"010-1111-2222")
        );
    }

    @Test
    void postPersonIfNameIsNull() throws Exception{
        PersonDto dto = new PersonDto();
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto))
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("message").value("이름은 필수값입니다"));
    }

    @Test
    void postPersonIfNameIsEmptyString() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName("");

        mockMvc.perform(
          MockMvcRequestBuilders.post("/api/person")
          .contentType(MediaType.APPLICATION_JSON)
          .content(toJsonString(dto))
        ).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
        .andExpect(jsonPath("$.message").value("이름은 필수값입니다"));
    }


    @Test
    void postPersonIfNameIsBlankString() throws Exception{
        PersonDto dto = new PersonDto();
        dto.setName(" ");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto))
        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름은 필수값입니다"));
    }

    @Test
    void modifyPersonIfNameIsDifferent() throws Exception{
        PersonDto dto = PersonDto.of("james","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto))
        ).andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("이름 변경을 허용하지 않습니다."));

    }

    @Test
    void modifyPersonIfPersonNotFound() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/10")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJsonString(dto))
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("Person Entity가 존재하지 않습니다"));
    };

    @Test
    void modifyPerson() throws Exception{
        PersonDto dto = PersonDto.of("martin","programming","판교",LocalDate.now(),"programmer","010-1111-2222");
        mockMvc.perform(
                MockMvcRequestBuilders.put("/api/person/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonString(dto))
                ).andExpect(status().isOk());
        Person result = personRepository.findById(1L).get();

        assertAll(
                () -> assertEquals(result.getName(),"martin"),
                () -> assertEquals(result.getHobby(),"programming"),
                () -> assertEquals(result.getAddress(),"판교"),
                () -> assertEquals(result.getBirthday(), Birthday.of(LocalDate.now())),
                () -> assertEquals(result.getPhoneNumber(),"010-1111-2222")
        );

    }

    @Test
    void modifyName() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/api/person/1")
                .param("name","martinModified"))
                .andExpect(status().isOk());

        assertEquals(personRepository.findById(1L).get().getName(), "martinModified");
    }

    @Test
    void deletePerson() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/person/1"))
                .andExpect(status().isOk());

        assertTrue(personRepository.findPeopleDeleted().stream().anyMatch(person -> person.getId().equals(1L)));
    }

    private String toJsonString(PersonDto personDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(personDto);
    }
}