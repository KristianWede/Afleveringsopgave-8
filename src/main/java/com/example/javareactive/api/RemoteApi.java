package com.example.javareactive.api;

import com.example.javareactive.dtos.AgeResponse;
import com.example.javareactive.dtos.CombinedResponse;
import com.example.javareactive.dtos.GenderResponse;
import com.example.javareactive.dtos.NationalResponse;
import com.example.javareactive.entity.Person;
import com.example.javareactive.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RemoteApi {

    PersonRepository personRepository;

    public RemoteApi(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    Mono<GenderResponse> getGenderForName(String name) {
        WebClient client = WebClient.create();
        Mono<GenderResponse> gender = client.get()
                .uri("https://api.genderize.io?name="+name)
                .retrieve()
                .bodyToMono(GenderResponse.class);
        return gender;
    }

    Mono<AgeResponse> getAgeForName(String name) {
        WebClient client = WebClient.create();
        Mono<AgeResponse> age = client.get()
                .uri("https://api.agify.io?name="+name)
                .retrieve()
                .bodyToMono(AgeResponse.class);
        return age;
    }

    Mono<NationalResponse> getNationalityForName(String name) {
        WebClient client = WebClient.create();
        Mono<NationalResponse> nationality = client.get()
                .uri("https://api.nationalize.io?name="+name)
                .retrieve()
                .bodyToMono(NationalResponse.class);
        return nationality;
    }


    public String callSlowEndpointNonBlocking(String name){
        long start = System.currentTimeMillis();
        Mono<GenderResponse> sr1 = getGenderForName(name);
        Mono<AgeResponse> sr2 = getAgeForName(name);
        Mono<NationalResponse> sr3 = getNationalityForName(name);

        var rs = Mono.zip(sr1,sr2,sr3).map(t-> {
            List<Object> randomStrings = new ArrayList<>();
            randomStrings.add(t.getT1());
            randomStrings.add(t.getT2());
            randomStrings.add(t.getT3());
            long end = System.currentTimeMillis();
            randomStrings.add(0,"Time spent NON-BLOCKING (ms): "+(end-start));
            return randomStrings;
        });
        List<Object> randoms = rs.block(); //We only block when all the three Mono's has fulfilled
        System.out.println(randoms.stream()
                .map(Object::toString)
                .collect(Collectors.joining(",")));

        return randoms.toString();
    }


    public CombinedResponse fetchNameDetails(String name) {
        Mono<GenderResponse> gender= getGenderForName(name);
        Mono<AgeResponse> age = getAgeForName(name);
        Mono<NationalResponse> nationality= getNationalityForName(name);

        Mono<Person> responseMono = Mono.zip(gender, age, nationality)
                .map(tuple -> new Person (tuple.getT1(),tuple.getT2(),tuple.getT3()));

        Person person = responseMono.block();

        personRepository.save(person);

        //Person person = new Person();

        CombinedResponse combinedResponse = new CombinedResponse(person);


        return combinedResponse;
    }




}
