package com.github.blaubaer.svgjd.swagger;

import com.github.blaubaer.svgjd.swagger.model.HelloMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import static org.springframework.http.ResponseEntity.ok;

@Controller
public class HelloApiImpl implements HelloApi {

    @Override
    public ResponseEntity<HelloMessage> helloGet(String name) {
        return ok(new HelloMessage()
                .content("Hello " + name + "!")
        );
    }

}
