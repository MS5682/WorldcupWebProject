package com.world.cup.controller;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.service.UserService;
import com.world.cup.service.WorldcupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;

    private final WorldcupService worldcupService;

    @GetMapping("/manager")
    public String manager(){
        return "/manager/manager_main.html";
    }

    @GetMapping("/manager/manager_memberList")
    public void manager_memberList(PageRequestDTO pageRequestDTO, Model model){

        model.addAttribute("result", userService.getMemberList(pageRequestDTO));
    }

    @GetMapping("/manager/manager_member")
    public void manager_member(String id,Model model){
        UserDTO userDTO = userService.getUser(id);
        model.addAttribute("dto",userDTO);
    }

    @PostMapping("/manager/manager_memberDelete")
    public String manager_memberDelete(String id, RedirectAttributes redirectAttributes){
        userService.DeleteMember(id);
        redirectAttributes.addFlashAttribute("msg",id);

        return("redirect:/manager/manager_memberList");
    }

    @PostMapping("/manager/disclosureUpdate")
    public String disclosureUpdate(int worldcupNum, RedirectAttributes redirectAttributes){
        worldcupService.updateDisclousre(worldcupNum);
        redirectAttributes.addFlashAttribute("msg",worldcupNum);

        return("redirect:/manager/manager_memberList");
    }


    @GetMapping("/manager/manager_report")
    public String manager_report(){
        return "/manager/manager_report.html";
    }

    @GetMapping("/manager/manager_reportContent")
    public String manager_reportContent(){
        return "/manager/manager_reportContent.html";
    }


    @GetMapping("/manager/manager_worldcup")
    public void manager_worldcup(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", worldcupService.getPublicWorldcupList(pageRequestDTO));

    }

    @GetMapping("/manager/manager_private")
    public void manager_private(PageRequestDTO pageRequestDTO, Model model){
        model.addAttribute("result", worldcupService.getPrivateWorldcupList(pageRequestDTO));
    }
}
