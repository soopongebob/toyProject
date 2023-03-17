package toyproject.community.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignUpForm {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;
    @NotEmpty(message = "이름은 필수입니다.")
    private String userName;
    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;
    @NotEmpty(message = "이메일은 필수입니다.")
    private String email;

}
