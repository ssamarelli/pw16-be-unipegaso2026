package com.pw.medicapp.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String TIME_FORMAT = "HH:mm";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            // Serializer: Da Java a JSON (per le risposte delle API)
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));

            // Deserializer: Da JSON a Java (per le richieste POST/PUT)
            builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
            builder.deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT)));
        };
    }
}