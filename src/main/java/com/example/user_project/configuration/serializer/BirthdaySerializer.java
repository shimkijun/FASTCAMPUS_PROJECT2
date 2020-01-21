package com.example.user_project.configuration.serializer;


import com.example.user_project.domain.dto.Birthday;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;

public class BirthdaySerializer extends JsonSerializer<Birthday> {

    @Override
    public void serialize(Birthday value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(LocalDate.of(value.getYearOfBirthday(),value.getMonthOfBirthday(),value.getDayOfBirthday()));
    }
}
