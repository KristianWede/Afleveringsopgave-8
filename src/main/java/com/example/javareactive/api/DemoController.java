package com.example.javareactive.api;

import com.example.javareactive.dtos.CombinedResponse;
import com.example.javareactive.repository.PersonRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {



    private final int SLEEP_TIME = 1000*3;

    RemoteApi remoteApi;

    PersonRepository personRepository;

    public DemoController(RemoteApi remoteApi, PersonRepository personRepository) {
        this.remoteApi = remoteApi;
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/random-string-slow")
    public String slowEndpoint() throws InterruptedException {
        Thread.sleep(SLEEP_TIME);
        return RandomStringUtils.randomAlphanumeric(10);
    }

    @GetMapping("/name-info")
    public CombinedResponse getNameInfo(@RequestParam("name") String name){

        if(personRepository.findById(name).isPresent()){
            System.out.println("Name already in database");
            return new CombinedResponse(personRepository.findById(name).get());

        }
        System.out.println("Name not found in database");
        return remoteApi.fetchNameDetails(name);

        //return remoteApi.callSlowEndpointNonBlocking(name);
    }




}
