package io.hysena.todoapp.service;

import io.hysena.todoapp.dto.SignupRequestDto;
import io.hysena.todoapp.entity.User;
import io.hysena.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder; //우리 입맛대로 정의해서 쓰기위해 WebSecurityConfig

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 사용자 입니다.");
        }

//        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User user = new User(username, requestDto.getPassword());
        userRepository.save(user);
    }
}
