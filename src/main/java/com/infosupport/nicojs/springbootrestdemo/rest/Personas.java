package com.infosupport.nicojs.springbootrestdemo.rest;

import com.infosupport.nicojs.springbootrestdemo.entities.Persona;
import com.infosupport.nicojs.springbootrestdemo.identity.IdentityLogger;
import com.infosupport.nicojs.springbootrestdemo.identity.PersonIdentity;
import com.infosupport.nicojs.springbootrestdemo.repositories.PersonasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/api/personas")
@Controller()
@Slf4j
public class Personas {

    private final PersonasRepository repo;

    @Autowired
    private PersonIdentity identity;

    @Autowired
    private IdentityLogger identityLogger;

    @Autowired
    public Personas(PersonasRepository repo) {
        this.repo = repo;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.printf("App started %s", event);
    }

    @GetMapping("{id}")
    public Persona get(@PathVariable("id") final int id) {
        log.info("starting request for {}", identity.getUserId());
        identityLogger.logUserId();
        return repo.findById(id).orElseThrow(() -> new NotFoundException(id, Persona.class.getSimpleName()));
    }
}
