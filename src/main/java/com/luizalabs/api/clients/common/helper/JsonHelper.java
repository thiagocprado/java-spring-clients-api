package com.luizalabs.api.clients.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

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

  public static String validateString(@NonNull String text) {
    return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
  }

  public static <T> Predicate<T> distinctByKey(@NonNull Function<? super T, Object> keyExtractor) {
    Map<Object, Boolean> map = new ConcurrentHashMap<>();

    return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
  }

  @SneakyThrows
  public <T> T convertJsonToClass(@NonNull String json, @NonNull TypeReference<T> pojoResult) {
    return this.mapper.readValue(json, pojoResult);
  }

  @SneakyThrows
  public <T> T convertJsonToClass(@NonNull String json, @NonNull Class<T> clazz) {
    return this.mapper.readValue(json, clazz);
  }

  @SneakyThrows
  public String convertHashToJson(@NonNull Map<Object, Object> param) {
    return this.mapper.writeValueAsString(param);
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
