package com.chance.coupchance.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class securityController {

    @PostMapping(path = "/security")
    public void register() {
        System.out.println("infos ");
    }
}
