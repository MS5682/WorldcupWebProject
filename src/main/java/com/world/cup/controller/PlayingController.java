package com.world.cup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.cup.service.PlayingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayingController {
    private final PlayingService playingService;

    @GetMapping("/playing")
    public String playing(int worldCupID, Model model) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(playingService.selectCandi(worldCupID));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.addAttribute("title", playingService.worldCupTitle(worldCupID));
        model.addAttribute("count", playingService.selectCandi(worldCupID).size());
        model.addAttribute("candi", json);

        return "/play/playing";
    }

    @GetMapping("/playResult")
    public void playResult(int worldCupID, Model model) {
        model.addAttribute("title", playingService.worldCupTitle(worldCupID));
    }
}
