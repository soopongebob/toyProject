package toyproject.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import toyproject.community.domain.Article;
import toyproject.community.domain.Comment;
import toyproject.community.domain.User;
import toyproject.community.service.ArticleService;
import toyproject.community.service.CommentService;
import toyproject.community.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 로그인 페이지
     *
     * @param model
     * @return "users/signIn"
     */
    @GetMapping("/users/signIn")
    public String signIn(Model model) {
        System.out.println("로그인 페이지");
        model.addAttribute("signInForm", new SignInForm());
        return "users/signIn";
    }

    /**
     * 로그인 확인
     *
     * @return "redirect:/"
     */
    @PostMapping("/users/signIn")
    public String singIn(@Valid SignInForm signInForm, HttpServletRequest request) {
        User userInfo = userService.signIn(signInForm.getUserId(), signInForm.getPassword());

        if (userInfo != null) {
            HttpSession session = request.getSession();
            session.setAttribute("userIdx", userInfo.getUserIdx());
            session.setAttribute("userId", userInfo.getUserId());
            session.setAttribute("userName", userInfo.getUserName());
            session.setAttribute("userImg", userInfo.getProfileImg());
            return "redirect:/";
        } else {
            return "users/signInFail";
        }
    }

    /**
     * 회원가입 페이지
     */
    @GetMapping("/users/signUp")
    public String signUp(Model model) {
        model.addAttribute("signUpForm", new SignUpForm());
        return "users/signUp";
    }

    /**
     * 회원가입 - 저장
     */
    @PostMapping("/users/signUp")
    public String signUp(@Valid SignUpForm signUpForm) {
        Long check = userService.signUp(signUpForm);
        if (check == 0) {
            return "users/signUpFail";
        } else {
            return "users/signIn";
        }
    }

    /**
     * 로그아웃
     */
    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        System.out.println("session = " + session);
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 마이페이지
     */
    @GetMapping("/users/myPage")
    public String myPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = userService.myPage(session.getAttribute("userIdx").toString());
        List<Article> articles = user.getArticles();
        List<Comment> comments = user.getComments();

        String imgPath = "/static/profileImg/" + user.getProfileImg();
        MyPageForm myPageForm = new MyPageForm();
        myPageForm.setUserId(user.getUserId());
        myPageForm.setUsername(user.getUserName());
        myPageForm.setProfileImg(imgPath);
        System.out.println("user.getProfileImg() = " + user.getProfileImg());
        myPageForm.setEmail(user.getEmail());
        myPageForm.setArticles(articles);
        myPageForm.setComments(comments);

        model.addAttribute("myPageForm", myPageForm);
        return "users/myPage";
    }

    @PostMapping("/users/myPage")
    public String myPage(@Valid MyPageForm myPageForm, @RequestParam(value="file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        System.out.println("file = " + file);
        String fileName = userService.myPageUpdate(session.getAttribute("userId").toString(), myPageForm, file);
        session.setAttribute("userName", myPageForm.getUsername());
        session.setAttribute("userId", myPageForm.getUserId());
        session.setAttribute("userImg", fileName);
        System.out.println("session.userImg = " + session.getAttribute("userImg"));
        return "redirect:/";
    }
}
