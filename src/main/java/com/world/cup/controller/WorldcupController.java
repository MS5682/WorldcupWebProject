package com.world.cup.controller;

import com.world.cup.dto.WorldcupDTO;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.WorldcupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/worldcup")
@RequiredArgsConstructor
public class WorldcupController {
    private final WorldcupService worldcupService;
    private final ChoiceService choiceService;
    @GetMapping("/list")
    public String list(){

        return "/user/my_worldcup.html";
    }

    @GetMapping("/register")
    public String getRegister(){

        return "/user/worldcup_register.html";
    }

    @PostMapping("/register")
    public String postRegister(WorldcupDTO worldcupDTO, RedirectAttributes redirectAttributes){
        int worldcupNum = worldcupService.register(worldcupDTO);
        redirectAttributes.addAttribute("worldcupNum",worldcupNum);
        return "redirect:/worldcup/edit";
    }

    @GetMapping("/edit")
    public String edit(WorldcupDTO worldcupDTO, Model model){
        log.info("get edit page");
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        worldcupDTO = choiceService.addChoiceToWorldcup(worldcupDTO);
        model.addAttribute("result", worldcupDTO);
        return "/user/worldcup_edit.html";
    }

    @GetMapping("/warning")
    public String warning(){

        return "/user/worldcup_register_warning.html";
    }
}
