package com.world.cup.service;

import com.world.cup.dto.UserDTO;
import com.world.cup.entity.User;
import lombok.Builder;

public interface UserService {

    void signup(UserDTO userDTO);

    boolean login(UserDTO userDTO);

    String findId(String email);
    boolean isIdExists(String id);

    boolean isEmailExists(String email);

    default User dtoToEntity(UserDTO userDTO){
        User user = User.builder().id(userDTO.getId()).email(userDTO.getEmail()).password(userDTO.getPassword())
                .build();

        return user;
    }

    default UserDTO entityToDto(User user){
        UserDTO userDTO = UserDTO.builder().id(user.getId()).email(user.getEmail()).build();

        return userDTO;
    }
}
