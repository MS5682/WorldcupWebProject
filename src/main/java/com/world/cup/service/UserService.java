package com.world.cup.service;

import com.world.cup.dto.*;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

    void signup(UserDTO userDTO);

    void googleSignup(GoogleDTO googleDTO);

    boolean login(UserDTO userDTO);

    String findId(String email);

    boolean findPassword(String id,String password);

    boolean editPassword(String id,String newPassword);
    boolean isIdExists(String id);

    boolean isEmailExists(String email);

    PageResultDTO<UserDTO, Object[]> getMemberList(PageRequestDTO pageRequestDTO);

    UserDTO getUser(String id);

    void DeleteMember(String id);

    boolean userCheck(UserDTO userDTO);



    default User dtoToEntity(UserDTO userDTO){
        User user = User.builder().id(userDTO.getId()).email(userDTO.getEmail()).password(userDTO.getPassword())
                .userRole(userDTO.getUserRole())
                .build();

        return user;
    }

    default User googleDtoToEntity(GoogleDTO googleDTO){
        User user = User.builder().id(googleDTO.getGid()).email(googleDTO.getEmail()).password("google")
              .userRole("user")
              .build();

        return user;
    }

    default UserDTO entityToDto(User user){
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .regDate(user.getRegDate())
                .userRole(user.getUserRole())
                .build();

        return userDTO;
    }

    default UserDTO entityToDto(User user,Long worldCupCount){

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .regDate(user.getRegDate())
                .userRole(user.getUserRole())
                .worldcupCount(worldCupCount)
                .build();

        return userDTO;
    }


}
