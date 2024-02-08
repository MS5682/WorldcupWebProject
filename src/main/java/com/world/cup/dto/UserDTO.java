package com.world.cup.dto;

import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    @NotBlank(message="아이디를 입력해주세요.")
    @Size(max=20,message="아이디는 20자 이하로 입력해주세요.")
    private String id;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max=40,message="비밀번호의 최대 크기를 초과했습니다.")
    private String password;

    private String userRole;
    private LocalDate regDate;

}
