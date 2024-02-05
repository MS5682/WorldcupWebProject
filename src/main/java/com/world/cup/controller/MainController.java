package com.world.cup.controller;

import com.world.cup.service.WorldcupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {
    private final WorldcupService worldcupService;
    @GetMapping("")
    public String main(){

        return "/main/main.html";
    }
}
