package com.world.cup.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class ManagerController {

    @GetMapping("/manager")
    public String manager(){
        return "/manager/manager_main.html";
    }

    @GetMapping("/manager/manager_memberList")
    public String manager_memberList(){
        return "/manager/manager_memberList.html";
    }

    @GetMapping("/manager/manager_member")
    public String manager_member(){
        return "/manager/manager_member.html";
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
