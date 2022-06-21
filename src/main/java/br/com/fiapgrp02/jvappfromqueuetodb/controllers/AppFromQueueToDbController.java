package br.com.fiapgrp02.jvappfromqueuetodb.controllers;

import br.com.fiapgrp02.jvappfromqueuetodb.services.AppFromQueueToDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppFromQueueToDbController {

    @Autowired
    private AppFromQueueToDbService service;

    @GetMapping("/test")
    public String testAppFromQueueToDb(){ return "FIAP 40SCJ : JvAppFromQueueToDb [2] : UP"; }

    @GetMapping("/get/{env}")
    public ResponseEntity getAllEntities(@PathVariable String env){
        return service.getAllEntities(env);
    }

}
