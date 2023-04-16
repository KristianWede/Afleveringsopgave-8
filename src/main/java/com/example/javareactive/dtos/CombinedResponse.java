package com.example.javareactive.dtos;

import com.example.javareactive.entity.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CombinedResponse {

    String name;
    String gender;
    double genderProbability;
    int age;
    int ageCount;
    String country;
    double countryProbability;

    public CombinedResponse(GenderResponse genderResponse, AgeResponse ageResponse, NationalResponse nationalResponse){
        this.name = genderResponse.getName();
        this.gender = genderResponse.getGender();
        this.genderProbability = genderResponse.getProbability();
        this.age = ageResponse.getAge();
        this.ageCount = ageResponse.getCount();
        this.country = nationalResponse.getCountry().get(0).getCountry_id();
        this.countryProbability = nationalResponse.getCountry().get(0).getProbability();
    }

    public CombinedResponse(Person person) {
        this.name = person.getName();
        this.gender = person.getGender();
        this.genderProbability = person.getGenderProbability();
        this.age = person.getAge();
        this.ageCount = person.getAgeCount();
        this.country = person.getCountry();
        this.countryProbability = person.getCountryProbability();
    }
}
