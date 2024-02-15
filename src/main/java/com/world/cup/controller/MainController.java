package com.world.cup.controller;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.service.WorldcupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {
    private final WorldcupService worldcupService;
    @GetMapping("")
    public String main(PageRequestDTO pageRequestDTO, Model model){
        log.info("main page");
        pageRequestDTO.setDisclosure((byte) 1);
        model.addAttribute("result", worldcupService.getWorldcupList(pageRequestDTO));
        log.info(worldcupService.getWorldcupList(pageRequestDTO));
        return "/main/main.html";
    }
}
