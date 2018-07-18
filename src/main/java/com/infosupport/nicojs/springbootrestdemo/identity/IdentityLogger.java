package com.infosupport.nicojs.springbootrestdemo.identity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class IdentityLogger {
    @Autowired
    private PersonIdentity identity;

    public void logUserId(){
        log.info("UserID: {}", identity.getUserId());
    }
}
