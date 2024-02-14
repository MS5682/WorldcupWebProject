package com.world.cup.controller;

import com.world.cup.dto.MailDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.service.EmailSendService;
import com.world.cup.service.UserService;
import com.world.cup.service.WorldcupService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final UserService userService;
    private final EmailSendService emailSendService;

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

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/worldcup/login";
    }

    @PostMapping("/findid")
    public String findid(@RequestParam String email,Model model){
        String id = userService.findId(email);
        System.out.println(id);
        String msg;
        boolean success = false;
        if (id != null) {
            success=true;
            msg = "당신의 아이디는 " + id + "입니다.";
        } else {
            msg = "아이디를 찾을 수 없습니다.";
        }
        model.addAttribute("success", success);
        model.addAttribute("msg", msg);

        return "findid/findid";
    }
    @PostMapping("/editpw")
    public String editpw(@RequestParam String pw, @RequestParam String npw
            , RedirectAttributes redirectAttributes, Model model, HttpSession session){
        String msg;
        int status;
        String id = (String) session.getAttribute("userId");
        System.out.println(id);
        System.out.println("input pw: "+pw);
        System.out.println("input npw: "+npw);

        if (userService.findPassword(id,pw)){
            if (userService.editPassword(id,npw)){
                msg = "비밀번호가 변경되었습니다.";
                status=1;
                redirectAttributes.addFlashAttribute("msg",msg);
                redirectAttributes.addFlashAttribute("status",status);
                return "redirect:/worldcup/editpw";
            }
            else {
                msg = "비밀번호가 변경에 실패했습니다.";
                status=3;
                redirectAttributes.addFlashAttribute("msg",msg);
                redirectAttributes.addFlashAttribute("status",status);
                return "redirect:/worldcup/editpw";
            }
        }
        else {
            msg = "현재 비밀번호가 일치하지 않습니다.";
            status=2;
            redirectAttributes.addFlashAttribute("msg",msg);
            redirectAttributes.addFlashAttribute("status",status);
            return "redirect:/worldcup/editpw";
        }




    }

    @PostMapping("/findpw")
    public String findpw(UserDTO userDTO, Model model) {
        String msg;
        boolean success = userService.userCheck(userDTO);

        if (success) {
            MailDTO mailDTO = emailSendService.createMailAndChargePassword(userDTO);
            emailSendService.mailSend(mailDTO);
            msg = "임시 비밀번호가 이메일로 발송되었습니다.";
        } else {
            msg = "비밀번호 찾기에 실패하셨습니다.";
        }

        model.addAttribute("success", success);
        model.addAttribute("msg", msg);

        return "findpw/findpw";
    }
}
