package com.world.cup.controller;

import com.world.cup.dto.ChoiceDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.WorldcupService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("/worldcup")
@RequiredArgsConstructor
public class WorldcupController {

    private final WorldcupService worldcupService;
    private final ChoiceService choiceService;

    @Autowired
    private Environment env;

    private String uploadDir;

    @GetMapping("/list")
    public String list(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, HttpSession session,
                       Model model, RedirectAttributes redirectAttributes){
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/";
        }

        pageRequestDTO.setUserId(userId);
        model.addAttribute("result", worldcupService.getWorldcupList(pageRequestDTO));
        return "/user/my_worldcup.html";
    }

    @GetMapping("/register")
    public String getRegister(HttpSession session, RedirectAttributes redirectAttributes){
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/";
        }
        return "/user/worldcup_register.html";
    }

    @PostMapping("/register")
    public String postRegister(WorldcupDTO worldcupDTO,HttpSession session, RedirectAttributes redirectAttributes){
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/";
        }
        worldcupDTO.setId(userId);
        worldcupDTO.setDisclosure((byte) 0);
        int worldcupNum = worldcupService.register(worldcupDTO);
        redirectAttributes.addAttribute("worldcupNum",worldcupNum);
        return "redirect:/worldcup/edit";
    }

    @PostMapping("/edit")
    public ResponseEntity<Boolean> edit(WorldcupDTO worldcupDTO) {
        worldcupService.modifyWorldcup(worldcupDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/edit")
    public String edit(@ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model,
                       HttpSession session, RedirectAttributes redirectAttributes){
        log.info("get edit page");
        WorldcupDTO worldcupDTO = WorldcupDTO.builder().worldcupNum(pageRequestDTO.getWorldcupNum()).build();
        worldcupDTO = worldcupService.getWorldcup(worldcupDTO);
        log.info(worldcupDTO);
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty() || !(worldcupDTO.getId().equals(userId))) {
            redirectAttributes.addFlashAttribute("message", "수정할 권한이 없습니다.");
            return "redirect:/";
        }
        PageResultDTO pageResultDTO = choiceService.getChoicePage(pageRequestDTO);
        worldcupDTO.setChoice(pageResultDTO.getDtoList());
        log.info(pageResultDTO.getStart());
        model.addAttribute("worldcup", worldcupDTO);
        model.addAttribute("choices", pageResultDTO);
        return "/user/worldcup_edit.html";
    }

    @PostMapping("/choice/upload")
    public ResponseEntity<Boolean> upload(ChoiceDTO choiceDTO) throws IOException {
        if(choiceDTO.getImage() != null){
            this.uploadDir = env.getProperty("user.dir") +File.separator + "src" +
                    File.separator + "main" + File.separator + "resources" +
                    File.separator + "uploads" + File.separator;
            MultipartFile file = choiceDTO.getImage();
            if(file.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
            }
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();

            String saveName = uploadDir + File.separator + folderPath + File.separator + uuid + "_" + fileName;
            Path savePath = Paths.get(saveName);

            file.transferTo(savePath);
            choiceDTO.setImgName(fileName);
            choiceDTO.setUuid(uuid);
            choiceDTO.setPath(folderPath);
        }

        if(choiceDTO.getChoiceNum() == 0)
            choiceService.addChoice(choiceDTO);
        else if(choiceDTO.getName() != null)
            choiceService.modifyChoiceName(choiceDTO);
        else if(choiceDTO.getUuid() == null)
            choiceService.modifyChoiceVideo(choiceDTO);
        else
            choiceService.modifyChoiceImg(choiceDTO);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    private String makeFolder() {

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        String folderPath =  str.replace("//", File.separator);
        this.uploadDir = env.getProperty("user.dir") +File.separator + "src" +
                File.separator + "main" + File.separator + "resources" +
                File.separator + "uploads" + File.separator;
        File uploadPathFolder = new File(uploadDir, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @PostMapping("/choice/removeFile")
    @Transactional
    public ResponseEntity<Boolean> removeFile(String fileName){

        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");
            this.uploadDir = env.getProperty("user.dir") +File.separator + "src" +
                    File.separator + "main" + File.separator + "resources" +
                    File.separator + "uploads" + File.separator;
            File file = new File(uploadDir +File.separator+ srcFileName);
            boolean result = file.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/choice/display")
    public ResponseEntity<byte[]> getFile(String fileName) {

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName =  URLDecoder.decode(fileName,"UTF-8");

            this.uploadDir = env.getProperty("user.dir") +File.separator + "src" +
                    File.separator + "main" + File.separator + "resources" +
                    File.separator + "uploads" + File.separator;
            File file = new File(uploadDir +File.separator+ srcFileName);

            HttpHeaders header = new HttpHeaders();

            header.add("Content-Type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @GetMapping("/warning")
    public String warning( HttpSession session, RedirectAttributes redirectAttributes){
        String userId = (String) session.getAttribute("userId");

        if (userId == null || userId.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "로그인 후 이용해주세요.");
            return "redirect:/";
        }
        return "/user/worldcup_register_warning.html";

    }

    @DeleteMapping("/choice/delete")
    public ResponseEntity<Boolean> choiceDelete(ChoiceDTO choiceDTO){
        choiceService.deleteChoice(choiceDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    @Transactional
    public ResponseEntity<Boolean> delete(WorldcupDTO worldcupDTO) {
        worldcupDTO = choiceService.getChoiceToWorldcup(worldcupDTO);
        log.info(worldcupDTO);
        for (ChoiceDTO choiceDTO : worldcupDTO.getChoice()) {
            if (choiceDTO.getUuid() != null) {
                String fileName = choiceDTO.getImageURL();
                removeFile(fileName);
            }
            choiceService.deleteChoice(choiceDTO);
        }
        worldcupService.deleteWorldcup(worldcupDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/view")
    public ResponseEntity<Boolean> view(WorldcupDTO worldcupDTO) {
        worldcupService.updateViewCnt(worldcupDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
