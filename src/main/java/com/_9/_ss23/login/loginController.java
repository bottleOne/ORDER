package com._9._ss23.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class loginController {

    @GetMapping ("/in")
    public String login(@RequestParam LoginDto loginDto){
        return "login";
    }
}
