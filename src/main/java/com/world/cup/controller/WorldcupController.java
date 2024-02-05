package com.world.cup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/worldcup")
@RequiredArgsConstructor
public class WorldcupController {
    @GetMapping("/list")
    public String list(){

        return "/user/my_worldcup.html";
    }

    @GetMapping("/register")
    public String register(){

        return "/user/worldcup_register.html";
    }

    @GetMapping("/edit")
    public String edit(){

        return "/user/worldcup_edit.html";
    }

    @GetMapping("/warning")
    public String warning(){

        return "/user/worldcup_register_warning.html";
    }
}
