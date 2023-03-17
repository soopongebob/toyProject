package toyproject.community.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@ToString(of = {"userId", "userName", "password", "flag"})
public class User extends BaseDTM {

    @Id @GeneratedValue
    private Long userIdx;

    @NotBlank
    private String userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String email;

    @ColumnDefault("none.PNG")
    private String profileImg;

    private String flag;

    @OneToMany(mappedBy = "user")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    public User() {
    }

    //연관관계 메서드------------------------------
    //Article
    public void addArticle(Article article) {
        //this article에 새로운 article을 list에 추가하고,
        articles.add(article);
        //article 객체의 User를 update된 list를 가진 this 로 새로 set 함
        article.setUser(this);
    }

    //Comment
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setUser(this);
    }
    //------------------------------------------

    /**
     * 회원가입
     */
    public void joinUser(String userId, String userName, String password, String email){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    /**
     * 회원정보수정
     */
    public void editUserInfo(String userName, String email, String profileImg){
        this.userName = userName;
        this.email = email;
        this.profileImg = profileImg;
    }

    /**
     * 회원탈퇴
     */
    public void exitUser(){
        this.flag = "D";
    }
}
