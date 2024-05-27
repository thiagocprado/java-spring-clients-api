package com.luizalabs.api.clients.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class JsonHelper {
    ObjectMapper mapper;

    public JsonHelper() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        this.mapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        mapper.registerModule(new JavaTimeModule());
        mapper.addHandler(new DeserializationProblemHandler() {
            @Override
            public Object handleWeirdStringValue(DeserializationContext ctxt,
                                                 Class<?> targetType,
                                                 String valueToConvert,
                                                 String failureMsg) {
                LocalDate date = LocalDate.parse(valueToConvert, DateTimeFormatter.ISO_DATE);

                return date.atStartOfDay(ctxt.getTimeZone().toZoneId());
            }
        });
    }

    @SneakyThrows
    public <T> T convertJsonToClass(@NonNull String json, @NonNull Class<T> clazz) {
        return this.mapper.readValue(json, clazz);
    }

    public <P, R, C> R buildClassPojoList(List<P> param, Class<C> clazz) throws JsonProcessingException {
        var pojo = this.mapper.getTypeFactory().constructCollectionType(List.class, clazz);

        return this.mapper.readValue(this.mapper.writeValueAsString(param), pojo);
    }

    public <P, R> R buildClassPojo(P param, Class<R> clazz) throws JsonProcessingException {
        return this.mapper.readValue(this.mapper.writeValueAsString(param), clazz);
    }

    @SneakyThrows
    public <P> String convertObjectToJson(P param) {
        return this.mapper.writeValueAsString(param);
    }
}
