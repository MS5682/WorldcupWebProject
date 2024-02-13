package com.world.cup.service;

import com.world.cup.dto.UserDTO;
import com.world.cup.entity.User;
import com.world.cup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public void signup(UserDTO userDTO) {
        User user = dtoToEntity(userDTO);
        user.setUserRole("user");
        userRepository.save(user);


    }

    @Override
    public boolean login(UserDTO userDTO) {
        Optional<User> result = userRepository.findByIdAndPassword(userDTO.getId(), userDTO.getPassword());
        return result.isPresent();
    }

    @Override
    public String findId(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        if(result.isPresent()){
            return result.get().getId();
        }else {
            return null;
        }

    }

    @Override
    public boolean isIdExists(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean userCheck(UserDTO userDTO) {
        Optional<User> result = userRepository.findByIdAndEmail(userDTO.getId(), userDTO.getEmail());

        if (result.isPresent() && result.get().getId().equals(userDTO.getId())
                && result.get().getEmail().equals(userDTO.getEmail())) {
            return true;
        } else {
            return false;
        }
    }


}
