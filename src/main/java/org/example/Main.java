package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {

        writeSingleObjectToJSON();

        readNodesOfJson();

        readObjectOfPersonFromJson();

        readMapOfPersonDataFromJson();

        readListOfPeopleFromJson();

    }

    private static void writeSingleObjectToJSON() throws IOException {
        Person person = new Person("Raegan Olson", 45, LocalDate.of(1983, Month.AUGUST, 12));

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(new JavaTimeModule());

        // if I need to export to file
        objectMapper.writeValue(new File("target/one-person.json"), person);

        // if we need the object as String
        String personAsJsonString = objectMapper.writeValueAsString(person);
        System.out.println("Output from write Single object to JSON");
        System.out.println(personAsJsonString);
    }

    private static void readNodesOfJson() throws JsonProcessingException {
        String personAsJsonString = """
                {"name":"Axel Doyle","age":29,"birthDate":"1993-08-12"}
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode jsonNode = objectMapper.readTree(personAsJsonString);
        String name = jsonNode.get("name").asText();
        Integer age = jsonNode.get("age").asInt();
        String date = jsonNode.get("birthDate").asText();
        System.out.println(" Age of " + name + " is: " + age + " date is: " + date);
    }

    private static void readObjectOfPersonFromJson() throws JsonProcessingException {
        String personAsJsonString = """
                {"name":"Charles Beasley","age":22,"birthDate":"1983-08-12"}
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Person person = objectMapper.readValue(personAsJsonString, new TypeReference<>(){});

        System.out.println();
        System.out.println("Output from read Object of Person from JSON");
        System.out.println("Person from String: " + person);
    }

    private static void readMapOfPersonDataFromJson() throws JsonProcessingException {
        String personAsJsonString = """
                {"name":"Tianna Fernandez","age":43,"birthDate":"1976-09-10"}
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, Object> map = objectMapper
                                    .readValue(personAsJsonString, new TypeReference<>(){});
        System.out.println();
        System.out.println("Output from read Map from JSON");
        map.forEach((k, v) -> System.out.println("Key: " + k + " has value: " + v));
    }

    private static void readListOfPeopleFromJson() throws IOException {
        String personAsJsonString = """
                [
                    {"name":"Emma Perez","age":10,"birthDate":"2010-01-01"},
                    {"name":"Alexander Joseph","age":11,"birthDate":"2009-01-01"}
                ]
                """;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        List<Person> people = objectMapper.readValue(personAsJsonString, new TypeReference<>(){});

        System.out.println();
        System.out.println("Output from read List of people");
        people.forEach(p -> System.out.println(p));

        // if I need to export to file
        objectMapper.writeValue(new File("target/people.json"), people);
    }
}