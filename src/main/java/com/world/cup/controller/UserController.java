package com.world.cup.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.cup.dto.*;
import com.world.cup.service.EmailSendService;
import com.world.cup.service.UserService;
import com.world.cup.service.WorldcupService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
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
    public String login(HttpSession session){
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }
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
        if(session.getAttribute("userId")!=null){
            redirectAttributes.addFlashAttribute("errormsg","이미 로그인 되어있습니다.");
            return "redirect:/";
        }
        if(loginSuccess) {
            session.setAttribute("userId", userDTO.getId());
            UserDTO userRoleDTO = userService.getUser(userDTO.getId());
            session.setAttribute("userRole",userRoleDTO.getUserRole());
            if(userRoleDTO.getUserRole().equals("admin")){
                return "redirect:/manager";
            }else {
                return "redirect:/";
            }
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
            if (id.equals("구글아이디로 가입된 계정")){
                msg = "구글아이디로 가입된 계정입니다.";
            }else if(id.equals("네이버아이디로 가입된 계정")){
                msg = "네이버아이디로 가입된 계정입니다.";
            }
            else {
                success=true;
                msg = "당신의 아이디는 " + id + "입니다.";
            }
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

    @PostMapping("/login/google")
    @ResponseBody
    @CrossOrigin(origins = "https://localhost:8000", allowedHeaders = "*", methods = {RequestMethod.POST}, allowCredentials = "true")
    public ResponseEntity<Map<String, String>>  loginWithGoogle(@RequestParam("googleId") String googleId,
                                                                @RequestParam("googleEmail") String googleEmail,
                                                  HttpSession session, HttpServletResponse response) {

//        session.setAttribute("googleId", googleId);
//        session.setAttribute("userId", googleId);

        if(googleId!=null){
            GoogleDTO googleDTO = new GoogleDTO();
            googleDTO.setGid(googleId);
            googleDTO.setEmail(googleEmail);
            userService.googleSignup(googleDTO);
        }

        Map<String, String> responseData = new HashMap<>();
//        responseData.put("redirectUrl", "/");

        return ResponseEntity.ok(responseData);
    }
    @PostMapping("/login/google/success")
    @ResponseBody
    public ResponseEntity<Map<String, String>>  loginWithGoogleSuccess(@RequestParam("googleId") String googleId,
                                                                @RequestParam("googleEmail") String googleEmail,
                                                                HttpSession session, HttpServletResponse response) {

        session.setAttribute("googleId", googleId);
        session.setAttribute("userId", googleId);

        Map<String, String> responseData = new HashMap<>();
        responseData.put("redirectUrl", "/");

        return ResponseEntity.ok(responseData);
    }

    @Value("${naver.client-id}")
        private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login/naver")
    public String naverLogin() {
        String naverAuthUrl = "https://nid.naver.com/oauth2.0/authorize";
        String state = "STATE";
        String naverLoginUrl = String.format("%s?response_type=code&client_id=%s&redirect_uri=%s&state=%s",
                naverAuthUrl, clientId, redirectUri, state);
        return "redirect:" + naverLoginUrl;
    }

    @GetMapping("/login/oauth2/code/naver")
    public String naverLoginCallback(@RequestParam("code") String code, @RequestParam("state") String state
            , HttpSession session,RedirectAttributes redirectAttributes){
        String naverAuthUrl = "https://nid.naver.com/oauth2.0/token";
        String naverAccessTokenUrl = String.format("%s?grant_type=authorization_code&client_id=%s&client_secret=%s&redirect_uri=%s&code=%s",
                naverAuthUrl, clientId, clientSecret, redirectUri, code);
        RestTemplate restTemplate = new RestTemplate();
        NaverTokenResponse naverTokenResponse = restTemplate.postForObject(naverAccessTokenUrl, null, NaverTokenResponse.class);

        if (naverTokenResponse != null && naverTokenResponse.getAccess_token() != null) {

            String naverUserInfoUrl = "https://openapi.naver.com/v1/nid/me";

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + naverTokenResponse.getAccess_token());

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<NaverUserInfoResponse> userInfoResponseEntity = restTemplate.exchange(
                    naverUserInfoUrl, HttpMethod.GET, entity, NaverUserInfoResponse.class);

            if (userInfoResponseEntity.getStatusCode().is2xxSuccessful()) {
                NaverUserInfoResponse userInfoResponse = userInfoResponseEntity.getBody();

                if (userInfoResponse != null && userInfoResponse.getResponse() != null) {
                    NaverUserInfo naverUserInfo = userInfoResponse.getResponse();
                    String naverUserId = naverUserInfo.getId();
                    String naverUserEmail = naverUserInfo.getEmail();

                    GoogleDTO googleDTO = new GoogleDTO();
                    if(userService.isEmailExists(naverUserEmail) && !userService.checkPassword(naverUserEmail).equals("naver")){
                        redirectAttributes.addFlashAttribute("msg","해당 이메일로 가입된 아이디가 있습니다.");
                        return "redirect:/worldcup/login";
                    }
                    else {
                        session.setAttribute("naverAccessToken", naverTokenResponse.getAccess_token());
                        session.setAttribute("userId", naverUserId);
                        session.setAttribute("naverEmail", naverUserEmail);
                        googleDTO.setGid(naverUserId);
                        googleDTO.setEmail(naverUserEmail);
                        userService.naverSignup(googleDTO);

                        return "redirect:/";
                    }

                }
            } else {
                System.err.println("네이버 유저정보를 찾을수 없습니다. 에러코드 : " +
                        userInfoResponseEntity.getStatusCodeValue());
            }
        } else {
            System.err.println("네이버 액세스 토큰을 찾을 수 없습니다.");
        }
        redirectAttributes.addFlashAttribute("msg","네이버 로그인에 실패했습니다.");
        return "redirect:/worldcup/login";

    }

    @GetMapping("/logout/naver")
    public String naverLogout(HttpSession session) {
        // 세션에서 저장된 네이버 액세스 토큰을 가져옵니다.
        String naverAccessToken = (String) session.getAttribute("naverAccessToken");

        // 네이버 액세스 토큰이 세션에 저장되어 있다면 로그아웃을 수행합니다.
        if (naverAccessToken != null) {
            String naverLogoutUrl = "https://nid.naver.com/oauth2.0/token";

            // 로그아웃을 위한 파라미터 설정
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("client_id", clientId);
            params.add("client_secret", clientSecret);
            params.add("access_token", naverAccessToken);
            params.add("service_provider", "NAVER");

            // RestTemplate을 사용하여 로그아웃 엔드포인트에 POST 요청을 보냅니다.
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(naverLogoutUrl, params, String.class);

            // 세션에서 네이버 액세스 토큰을 삭제합니다.
            session.invalidate();
        }

        // 로그아웃 후 리다이렉트
        return "redirect:/worldcup/login";
    }


}
