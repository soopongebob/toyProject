package toyproject.community.controller;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignInForm {

    @NotEmpty(message = "아이디는 필수입니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수입니다.")
    private String password;

}
