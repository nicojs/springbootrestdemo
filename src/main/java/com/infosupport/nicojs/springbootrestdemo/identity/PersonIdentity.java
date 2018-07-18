package com.infosupport.nicojs.springbootrestdemo.identity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonIdentity {
    private static int nextUserId = 0;
    private final int userId;

    public PersonIdentity(){
        this.userId = nextUserId++;
    }
}
