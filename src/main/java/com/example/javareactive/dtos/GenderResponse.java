package com.example.javareactive.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenderResponse {
    String gender;
    String name;
    int count;
    double probability;
}


