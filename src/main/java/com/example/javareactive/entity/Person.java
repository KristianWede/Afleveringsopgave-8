package com.example.javareactive.entity;

import com.example.javareactive.dtos.AgeResponse;
import com.example.javareactive.dtos.CombinedResponse;
import com.example.javareactive.dtos.GenderResponse;
import com.example.javareactive.dtos.NationalResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Person {

    @Id
    private String name;
    private String gender;
    private double genderProbability;
    private int age;
    private int ageCount;
    private String country;
    private double countryProbability;

    public Person(String name, String gender, double genderProbability, int age, int ageCount, String country, double countryProbability) {
        this.name = name;
        this.gender = gender;
        this.genderProbability = genderProbability;
        this.age = age;
        this.ageCount = ageCount;
        this.country = country;
        this.countryProbability = countryProbability;
    }

    public Person(CombinedResponse combinedResponse){
        this.name = combinedResponse.getName();
        this.gender = combinedResponse.getGender();
        this.genderProbability = combinedResponse.getGenderProbability();
        this.age = combinedResponse.getAge();
        this.ageCount = combinedResponse.getAgeCount();
        this.country = combinedResponse.getCountry();
        this.countryProbability = combinedResponse.getCountryProbability();
    }

    public Person(GenderResponse genderResponse, AgeResponse ageResponse, NationalResponse nationalResponse){
        this.name = genderResponse.getName();
        this.gender = genderResponse.getGender();
        this.genderProbability = genderResponse.getProbability();
        this.age = ageResponse.getAge();
        this.ageCount = ageResponse.getCount();
        this.country = nationalResponse.getCountry().get(0).getCountry_id();
        this.countryProbability = nationalResponse.getCountry().get(0).getProbability();
    }

}
