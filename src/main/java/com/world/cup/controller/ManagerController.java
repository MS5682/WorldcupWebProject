package com.world.cup.controller;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;
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

    @GetMapping("/manager/manager_report")
    public String manager_report(){
        return "/manager/manager_report.html";
    }

    @GetMapping("/manager/manager_reportContent")
    public String manager_reportContent(){
        return "/manager/manager_reportContent.html";
    }


    @GetMapping("/manager/manager_worldcup")
    public String manager_worldcup(){
        return "/manager/manager_worldcup.html";
    }

    @GetMapping("/manager/manager_private")
    public String manager_private(){
        return "/manager/manager_private.html";
    }
}
