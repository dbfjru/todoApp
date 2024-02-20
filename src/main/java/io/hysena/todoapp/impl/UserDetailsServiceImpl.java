package io.hysena.todoapp.impl;

import io.hysena.todoapp.entity.User;
import io.hysena.todoapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

//    public UserDetails getUserDetails(String username){
//        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Not Found " + username));
//        return new UserDetailsImpl(user);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Not Found " + username));
        return new UserDetailsImpl(user);
    }
}
