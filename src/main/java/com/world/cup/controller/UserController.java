package com.world.cup.controller;

import com.world.cup.dto.UserDTO;
import com.world.cup.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/worldcup")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/login")
    public String login(){

        return "/login/login.html";
    }

    @GetMapping("/signup")
    public String signup(){

        return "/signup/signup.html";
    }

    @GetMapping("/findid")
    public String findid(){

        return "/findid/findid.html";
    }

    @GetMapping("/findpw")
    public String findpw(){

        return "/findpw/findpw.html";
    }

    @GetMapping("/editpw")
    public String editpw(){

        return "/editpw/editpw.html";
    }

    @GetMapping("/checkIdDuplicate")
    @ResponseBody
    public Map<String, Boolean> checkIdDuplicate(@RequestParam String id) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("idDuplicate", userService.isIdExists(id));
        return response;
    }

    @GetMapping("/checkEmailDuplicate")
    @ResponseBody
    public Map<String, Boolean> checkEmailDuplicate(@RequestParam String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("emailDuplicate", userService.isEmailExists(email));
        return response;
    }

    @GetMapping("/checkIdAndEmailDuplicate")
    @ResponseBody
    public Map<String, Boolean> checkIdAndEmailDuplicate(@RequestParam String id, @RequestParam String email) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("idDuplicate", userService.isIdExists(id));
        response.put("emailDuplicate", userService.isEmailExists(email));
        return response;
    }

    @PostMapping("/signup")
    public String signup(@Valid UserDTO userDTO, Errors errors,Model model){

        if(errors.hasErrors()){
            model.addAttribute("msg","회원가입에 실패했습니다.");
            return "/signup/signup.html";
        }else {
            userService.signup(userDTO);
            return "redirect:/worldcup/login";
        }

    }

    @PostMapping("/login")
    public String login(UserDTO userDTO, HttpSession session, RedirectAttributes redirectAttributes){
        boolean loginSuccess = userService.login(userDTO);
        if(loginSuccess) {
            session.setAttribute("userId", userDTO.getId());
            return "redirect:/worldcup/list";
        }
        else {
            redirectAttributes.addFlashAttribute("msg", "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
            return "redirect:/worldcup/login";
        }

    }

    @PostMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/worldcup/login";
    }
}
