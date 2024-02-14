package com.world.cup.service;

import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;

import java.util.List;
import java.util.stream.Collectors;

public interface UserService {

    void signup(UserDTO userDTO);

    boolean login(UserDTO userDTO);

    String findId(String email);
    boolean isIdExists(String id);

    boolean isEmailExists(String email);

    PageResultDTO<UserDTO, Object[]> getMemberList(PageRequestDTO pageRequestDTO);

    UserDTO getUser(String id);

    void DeleteMember(String id);

    boolean userCheck(UserDTO userDTO);



    default User dtoToEntity(UserDTO userDTO){
        User user = User.builder().id(userDTO.getId()).email(userDTO.getEmail()).password(userDTO.getPassword())
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

    default UserDTO entityToDto(User user,Worldcup worldcup){
        String title = "";
        if (worldcup != null) {
            title = worldcup.getTitle();
        }
        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .regDate(user.getRegDate())
                .userRole(user.getUserRole())
                .worldCupTitle(title)
                .build();

        return userDTO;
    }


}
