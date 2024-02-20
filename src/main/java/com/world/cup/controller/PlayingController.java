package com.world.cup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.PlayingService;
import com.world.cup.service.WorldcupService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayingController {
    private final PlayingService playingService;
    private final WorldcupService worldcupService;
    private final ChoiceService choiceService;

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

//    @GetMapping("/playResult")
//    public void playResult(int worldCupID, Model model) {
//        model.addAttribute("title", playingService.worldCupTitle(worldCupID));
//    }

    @GetMapping("/playResult")
    public void playResult(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model){
        WorldcupDTO worldcupDTO = WorldcupDTO.builder().worldcupNum(pageRequestDTO.getWorldcupNum()).build();
        WorldcupDTO choiceRank = choiceService.getChoiceRank(worldcupDTO);
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        Integer sumFirst = choiceService.sumFirst(worldcupDTO);
        pageRequestDTO.setOrder(1);
        PageResultDTO pageResultDTO = choiceService.getChoicePage(pageRequestDTO);
        worldcupDTO.setChoice(pageResultDTO.getDtoList());
        WorldcupDTO worldcupChart = WorldcupDTO.builder().worldcupNum(worldcupDTO.getWorldcupNum()).build();
        worldcupChart = choiceService.getTopTen(worldcupChart);

        log.info(worldcupChart.getChoice());
        model.addAttribute("worldcup", worldcupDTO);
        model.addAttribute("sumFirst", sumFirst);
        model.addAttribute("rank", choiceRank);
        model.addAttribute("chart",worldcupChart.getChoice());
        model.addAttribute("choices", pageResultDTO);
    }
}
