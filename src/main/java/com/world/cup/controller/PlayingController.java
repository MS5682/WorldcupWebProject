package com.world.cup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/play")
public class PlayingController {
    @GetMapping("/playing")
    public void playing() {

    }

    @GetMapping("/playResult")
    public void playResult() {

    }
}
