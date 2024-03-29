package com.world.cup.controller;

import com.world.cup.dto.*;
import com.world.cup.service.ChoiceService;
import com.world.cup.service.CommentService;
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
import java.util.UUID;

@Controller
@Log4j2
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(CommentDTO commentDTO, HttpSession session) {
        commentDTO.setId((String) session.getAttribute("userId"));
        commentService.addComment(commentDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<Boolean> edit(CommentDTO commentDTO) {
        commentService.modifyComment(commentDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(CommentDTO commentDTO) {
        commentService.deleteComment(commentDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
