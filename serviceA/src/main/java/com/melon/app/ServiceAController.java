package com.melon.app;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muskmelon
 * @since 1.0
 */
@RestController
public class ServiceAController {

    @RequestMapping(value = "/sayHello/{name}", method = RequestMethod.GET)
    public String sayHello(@PathVariable("name") String name) {
        System.out.println("调用一次");
        return "Hello, " + name;
    }
}
