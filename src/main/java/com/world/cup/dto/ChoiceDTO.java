package com.world.cup.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChoiceDTO {
    private int worldcupNum;
    private int choiceNum;
    private int choiceSeq;
    private String name;
    private Byte type;
    private String path;
    private String uuid;
    private String imgName;
    private int first;
    private MultipartFile image;
    private int win;
    private int lose;

    public String getImageURL(){
        try {
            return URLEncoder.encode(path+"/"+uuid+"_"+imgName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
