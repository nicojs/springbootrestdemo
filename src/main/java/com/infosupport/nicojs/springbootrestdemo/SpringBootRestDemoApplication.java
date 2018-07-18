package com.infosupport.nicojs.springbootrestdemo;

import com.infosupport.nicojs.springbootrestdemo.identity.Identified;
import com.infosupport.nicojs.springbootrestdemo.identity.PersonIdentity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.SQLException;

@SpringBootApplication()
public class SpringBootRestDemoApplication {

    public static void main(String[] args) throws SQLException {
        DatabaseCreator.declareDatabase();
        SpringApplication.run(SpringBootRestDemoApplication.class, args);
    }


    @Bean
    @RequestScope
    public PersonIdentity identity() {
        return new PersonIdentity();
    }

}
