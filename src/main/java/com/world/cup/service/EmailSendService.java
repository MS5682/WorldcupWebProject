package com.world.cup.service;

import com.world.cup.dto.MailDTO;
import com.world.cup.dto.UserDTO;
import com.world.cup.dto.WorldcupDTO;
import com.world.cup.entity.User;
import com.world.cup.entity.Worldcup;
import com.world.cup.repository.UserRepository;
import com.world.cup.repository.WorldcupRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;


@Service
@RequiredArgsConstructor
public class EmailSendService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final WorldcupRepository worldcupRepository;
    private static final String FROM_ADDRESS = "tndus28901@gmail.com";

    public MailDTO createMailAndChargePassword(UserDTO userDTO) {
        String str = getTempPassword();
        MailDTO dto = new MailDTO();
        dto.setAddress(userDTO.getEmail());
        dto.setTitle("[마이픽] "+userDTO.getId()+"님의 임시비밀번호 안내 이메일 입니다.");
        dto.setMessage("안녕하세요. 마이픽에서 보내는 임시비밀번호 안내 관련 메일 입니다.\n\n" + " " + userDTO.getId() + " " + "님의 임시 비밀번호는 "
                + str +" 입니다.\n\n 로그인 후에 비밀번호를 변경해주세요.");
        updatePassword(str,userDTO);
        return dto;
    }

    public MailDTO deleteAndMessage(String id,int worldcupNum,String msg) {
        MailDTO dto = new MailDTO();
        User user = userRepository.getUserById(id);
        Worldcup worldcup =(Worldcup) worldcupRepository.getWorldcupByWorldcupNum(worldcupNum);
        dto.setAddress(user.getEmail());

        if (user.getPassword().equals("google") || user.getPassword().equals("naver")){
            dto.setTitle("[마이픽] "+user.getEmail()+"님의 ["+worldcup.getTitle()+"]이 신고로 인해 삭제되었습니다.");
            dto.setMessage("안녕하세요. 마이픽에서 보내는 월드컵 삭제 관련 메일 입니다.\n\n" + " " + user.getEmail() + " " + "님의 "
                    +worldcup.getTitle()+"이 신고로 인해 삭제되었습니다\n\n"
                    + "삭제 사유는 다음과 같습니다\n\n" + msg);
        }else {
            dto.setTitle("[마이픽] "+id+"님의 ["+worldcup.getTitle()+"]이 신고로 인해 삭제되었습니다.");
            dto.setMessage("안녕하세요. 마이픽에서 보내는 월드컵 삭제 관련 메일 입니다.\n\n" + " " + id + " " + "님의 "
                    +worldcup.getTitle()+"이 신고로 인해 삭제되었습니다\n\n"
                    + "삭제 사유는 다음과 같습니다\n\n" + msg);

        }

        return dto;
    }

    public void updatePassword(String str, UserDTO userDTO) {
        String pw =  str;
        User user = userRepository.findById(userDTO.getId()).get();
        user.setPassword(pw);
        userRepository.save(user);
    }

    public String getTempPassword() {
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        String str = "";

        int idx = 0;
        for (int i=0; i<10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    public void mailSend(MailDTO mailDto) {
        System.out.println("이메일 전송 완료");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }

    public static String encryptSHA256(String s) {
        return encrypt(s, "SHA-256");
    }

    public static String encryptMD5(String s) {
        return encrypt(s, "MD5");
    }

    public static String encrypt(String s, String messageDigest) {
        try {
            MessageDigest md = MessageDigest.getInstance(messageDigest);
            byte[] passBytes = s.getBytes();
            md.reset();
            byte[] digested = md.digest(passBytes);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < digested.length; i++) sb.append(Integer.toString((digested[i]&0xff) + 0x100, 16).substring(1));
            return sb.toString();
        } catch (Exception e) {
            return s;
        }
    }

}
