package io.hysena.todoapp.repository;

import io.hysena.todoapp.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("findByUsername 테스트 - 성공")
    void testFindByUsernamePass(){
        //given
        User user = new User("username","password");
        userRepository.save(user);
        //when
        User testUser = userRepository.findByUsername("username").orElseThrow(()->new UsernameNotFoundException("Username Not Found"));

        //then
        assertEquals(user.getUserId(), testUser.getUserId());
        assertEquals(user.getUsername(), testUser.getUsername());
        assertEquals(user.getPassword(), testUser.getPassword());
        assertEquals(user, testUser);
    }
}