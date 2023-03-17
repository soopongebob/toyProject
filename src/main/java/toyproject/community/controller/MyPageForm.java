package toyproject.community.controller;

import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import toyproject.community.domain.Article;
import toyproject.community.domain.Comment;

import java.util.List;

@Data
@ToString
public class MyPageForm {

    private String userId;
    private String username;
    private String email;
    private String profileImg;
    private MultipartFile file;
    private List<Article> articles;
    private List<Comment> comments;

    public MyPageForm() {
    }

    public MyPageForm(String userId, String username, String email, String profileImg, MultipartFile file, List<Article> articles, List<Comment> comments) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.profileImg = profileImg;
        this.file = file;
        this.articles = articles;
        this.comments = comments;
    }


}
