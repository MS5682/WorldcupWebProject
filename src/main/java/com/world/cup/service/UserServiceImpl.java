package com.world.cup.service;

import com.world.cup.dto.GoogleDTO;
import com.world.cup.dto.PageRequestDTO;
import com.world.cup.dto.PageResultDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void googleSignup(GoogleDTO googleDTO) {
        User user = googleDtoToEntity(googleDTO);
        userRepository.save(user);

    }

    @Override
    public void naverSignup(GoogleDTO googleDTO) {
        if(!userRepository.existsByEmail(googleDTO.getEmail())){
            User user = naverDtoToEntity(googleDTO);
            userRepository.save(user);
        }

    }

    @Override
    public String checkPassword(String email) {
        Optional<User> result = userRepository.findByEmail(email);
        if (result.isPresent()){
            return result.get().getPassword();
        }
        return null;
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
            if(result.get().getPassword().equals("google")){
                return "구글아이디로 가입된 계정";
            }else if(result.get().getPassword().equals("naver")){
                return "네이버아이디로 가입된 계정";
            }
            else {
                return result.get().getId();
            }
        }else {
            return null;
        }


    }

    @Override
    public boolean findPassword(String id,String password) {
        Optional<User> result = userRepository.findById(id);

        if(result.isPresent()){
            String pw = result.get().getPassword();
            System.out.println("DB pass:"+pw);
            if (pw.equals(password)){
                System.out.println("input current pass:"+password);
                return true;
            }else {
                System.out.println("incorrect password");
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean editPassword(String id,String newPassword) {
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()){
            User user = result.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
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
    public PageResultDTO<UserDTO, Object[]> getMemberList(PageRequestDTO requestDTO) {
        Sort sort = null;
        sort = Sort.by("regDate").descending();
        Page<Object[]> result = userRepository.getMemberListPage
                (requestDTO.getType(),
                        requestDTO.getKeyword(),
                        requestDTO.getPageable(sort));

        Function<Object[], UserDTO> fn = (arr -> entityToDto((User)arr[0],(Long)arr[1]));

        return new PageResultDTO<>(result, fn);

    }

    @Override
    public UserDTO getUser(String id) {
        User result = userRepository.getUserById(id);


        return entityToDto(result);
    }

    @Transactional
    @Override
    public void DeleteMember(String id) {
        userRepository.deleteById(id);
        //user 테이블의 id를 외래키로 가지고 있는 걸 전부 삭제해야함
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
