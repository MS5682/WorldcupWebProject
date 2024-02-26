package com.world.cup.dto;

import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO {
    private int commentNum;
    private String content;
    private int type;
    private int worldcupNum;
    private Integer choiceNum;
    private String choiceName;
    private String id;
    private String email;
    private String password;
    private LocalDateTime regDate;
}
