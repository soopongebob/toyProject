package toyproject.community.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.community.controller.SignUpForm;
import toyproject.community.domain.User;
import toyproject.community.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    /**
     * 회원가입 테스트
     */
    @Test
    @Transactional
    @Rollback(value = true)
    public void signUp() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setUserId("testUser");
        signUpForm.setEmail("testUser@test");
        signUpForm.setUserName("testUser");
        signUpForm.setPassword("testUser");
        Long aLong = userService.signUp(signUpForm);

        if(aLong != 0L){
            User user = userRepository.findByUserId(signUpForm.getUserId());

            Assertions.assertThat(user.getUserId()).isEqualTo(signUpForm.getUserId());
            Assertions.assertThat(user.getUserName()).isEqualTo(signUpForm.getUserName());
            Assertions.assertThat(user.getEmail()).isEqualTo(signUpForm.getEmail());
            Assertions.assertThat(user.getPassword()).isEqualTo(signUpForm.getPassword());
        }
    }

}