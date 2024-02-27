package com.world.cup.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.Choice;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.CommentService;
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
import com.world.cup.dto.SaveDTO;
import com.world.cup.service.ProceedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/play")
@RequiredArgsConstructor
public class PlayingController {
    private final PlayingService playingService;
    private final WorldcupService worldcupService;
    private final ChoiceService choiceService;
    private final ProceedService proceedService;
    private final CommentService commentService;


    @GetMapping("/playing")
    public String playing(int worldCupID, Model model) {
        boolean checksave = proceedService.havesave("test1234", worldCupID);
        System.out.println("checksave 확인");
        System.out.println(checksave);

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
        model.addAttribute("issave", checksave);

        return "/play/playing";
    }

    @GetMapping("/playing/loadsave")
    public ResponseEntity<String> saveplay(String userId, int worldcupId) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String json = null;
        try {
            json = mapper.writeValueAsString(proceedService.savefileload(userId, worldcupId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<Choice> choiceList = proceedService.savefileload(userId, worldcupId);
        System.out.println("controller choiceList----------------------");
        System.out.println(choiceList);

        System.out.println("json 확인");
        System.out.println(json);

        return ResponseEntity.ok(json);
    }

    @GetMapping("/playing/next")
    public ResponseEntity<int[]> round(String userId, int worldcupId) {
        int[] nextArr = proceedService.round(userId, worldcupId);

        return ResponseEntity.ok(nextArr);
    }

    @GetMapping("/playing/savedelete")
    public ResponseEntity<String> savedelete(String userId, int worldcupId) {
        proceedService.savedelete(userId, worldcupId);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/playResult")
    public void playResult(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model, HttpSession session){
        WorldcupDTO worldcupDTO = WorldcupDTO.builder().worldcupNum(pageRequestDTO.getWorldcupNum()).build();
        WorldcupDTO choiceRank = choiceService.getChoiceRank(worldcupDTO);
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        Integer sumFirst = choiceService.sumFirst(worldcupDTO);
        pageRequestDTO.setOrder(1);
        PageResultDTO pageResultDTO = choiceService.getChoicePage(pageRequestDTO);
        worldcupDTO.setChoice(pageResultDTO.getDtoList());
        String userId = (String) session.getAttribute("userId");
        model.addAttribute("userId", userId);
        WorldcupDTO worldcupChart = WorldcupDTO.builder().worldcupNum(worldcupDTO.getWorldcupNum()).build();
        worldcupChart = choiceService.getTopTen(worldcupChart);
        pageRequestDTO.setCommentType(0);
        model.addAttribute("comment", commentService.getCommentPage(pageRequestDTO));
        pageRequestDTO.setCommentType(1);
        model.addAttribute("request", commentService.getCommentPage(pageRequestDTO));
        model.addAttribute("worldcup", worldcupDTO);
        model.addAttribute("sumFirst", sumFirst);
        model.addAttribute("rank", choiceRank);
        model.addAttribute("chart",worldcupChart.getChoice());
        model.addAttribute("choices", pageResultDTO);
    }

    @GetMapping("/playing/roulette")
    public String roulette(WorldcupDTO worldcupDTO,Model model){
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        worldcupDTO = choiceService.getChoiceToWorldcup(worldcupDTO);
        model.addAttribute("worldcup", worldcupDTO);

        return "/play/roulette.html";
    }

    @PostMapping("/playing/save")
    public ResponseEntity<String> save(@RequestBody SaveDTO saveDTO) {
        System.out.println("saveDTO 확인용-----------------------------------");
        System.out.println(saveDTO);
        proceedService.save(saveDTO);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/playing/autosave")
    public ResponseEntity<String> autosave(@RequestBody SaveDTO saveDTO) {
        proceedService.autosave(saveDTO);

        return ResponseEntity.ok("success");
    }

    @PostMapping("/playing/finalsave")
    public ResponseEntity<String> finalsave(@RequestBody SaveDTO saveDTO) {
        proceedService.finalsave(saveDTO);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/playing/quiz")
    public String quiz(int worldCupID,Model model){

        model.addAttribute("worldcup", playingService.worldCupTitle(worldCupID));
        model.addAttribute("quiz", playingService.selectQuiz(worldCupID));
        model.addAttribute("count", playingService.selectQuiz(worldCupID).size());

        return "/play/quiz.html";
    }
}
