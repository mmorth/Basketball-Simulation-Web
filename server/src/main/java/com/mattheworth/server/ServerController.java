//package com.mattheworth.server;
//
//import java.util.concurrent.atomic.AtomicLong;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//@RestController
//public class ServerController {
//
//    private final AtomicLong counter = new AtomicLong();
//
//    @RequestMapping(value = "/teams", method = RequestMethod.GET)
//    public Team team() {
//        return new Team(1, "Sample Team", 100, 90);
//    }
//    
//    // @RequestParam(value="name") String name
//}
