package com.world.cup.controller;

import com.world.cup.dto.*;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.ReportService;
import com.world.cup.service.UserService;
import com.world.cup.service.WorldcupService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;

    private final WorldcupService worldcupService;

    private final ReportService reportService;

    private final ChoiceService choiceService;

    @GetMapping("")
    public String manager(HttpSession session, HttpServletRequest request){
        String userRole = (String)session.getAttribute("userRole");
        if(userRole!=null&&userRole.equals("admin")){
            return "/manager/manager_main.html";
        }else{
            return "/login/login.html";
        }

    }

    @GetMapping("/manager_memberList")
    public void manager_memberList(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", userService.getMemberList(pageRequestDTO));

    }

    @GetMapping("/manager_member")
    public void manager_member(String id,Model model){
        UserDTO userDTO = userService.getUser(id);
        model.addAttribute("dto",userDTO);
    }

//    @PostMapping("/manager_memberDelete")
//    public String manager_memberDelete(String id, RedirectAttributes redirectAttributes){
//        userService.DeleteMember(id);
//        redirectAttributes.addFlashAttribute("msg",id);
//
//        return("redirect:/manager/manager_memberList");
//    }
    //Restful 방식으로 변경
    @DeleteMapping("/{id}/manager_memberDelete")
    public ResponseEntity<Void> memberDelete(@PathVariable String id){
        userService.DeleteMember(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/disclosureUpdate")
//    public String disclosureUpdate(int worldcupNum, RedirectAttributes redirectAttributes){
//        worldcupService.updateDisclousre(worldcupNum);
//        redirectAttributes.addFlashAttribute("msg",worldcupNum);
//
//        return("redirect:/manager/manager_memberList");
//    }
    //Restful 방식으로 변경
    @PutMapping("/{worldcupNum}/disclosureUpdate")
    public ResponseEntity<Void> updateWorldcupDisclosure(@PathVariable int worldcupNum) {
        worldcupService.updateDisclousre(worldcupNum);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/manager_report")
    public void manager_reportList(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", reportService.getReportList(pageRequestDTO));

    }

    @PostMapping("/manager_report")
    public String manager_report(ReportDTO reportDTO){
        log.info(reportDTO);
        reportService.report(reportDTO);

        return "redirect:/";
    }

    @GetMapping("/manager_reportContent")
    public void manager_reportContent(String reportId,Model model){
        ReportDTO reportDTO = reportService.getReportContent(reportId);
        model.addAttribute("dto",reportDTO);

    }

    @GetMapping("/manager_checkWorldcup")
    public String edit(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model,
                       HttpSession session, RedirectAttributes redirectAttributes){
        log.info("get edit page");
        WorldcupDTO worldcupDTO = WorldcupDTO.builder().worldcupNum(pageRequestDTO.getWorldcupNum()).build();
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        log.info(worldcupDTO);
        String userId = (String) session.getAttribute("userId");


        PageResultDTO pageResultDTO = choiceService.getChoicePage(pageRequestDTO);
        worldcupDTO.setChoice(pageResultDTO.getDtoList());
        log.info(pageResultDTO.getStart());
        model.addAttribute("worldcup", worldcupDTO);
        model.addAttribute("choices", pageResultDTO);
        return "/manager/manager_checkWorldcup.html";
    }


    @GetMapping("/manager_worldcup")
    public void manager_worldcup(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", worldcupService.getPublicWorldcupList(pageRequestDTO));

    }

    @GetMapping("/manager_private")
    public void manager_private(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", worldcupService.getPrivateWorldcupList(pageRequestDTO));
    }
}
