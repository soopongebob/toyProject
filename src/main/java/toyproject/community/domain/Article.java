package toyproject.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"articleIdx", "category", "subject", "contents", "createDTM", "modifyDTM","flag", "viewCount"})       //ToString에 list는 포함하면 안됨
public class Article extends BaseDTM{

    @Id @GeneratedValue
    private Long articleIdx;
    @NotBlank
    private String subject;
    @NotNull
    private String contents;

    private Long viewCount = 0L;

    private String flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "article")
    private List<Comment> comments = new ArrayList<>();

    //연관관계 메서드----------------------
    //User
    public void setUser(User user) {
        //현재 가지고있는 User 객체를 변경하고,
        this.user = user;
        //user객체의 article에 this 객체를 추가함
        user.getArticles().add(this);
    }

    //Comment
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setArticle(this);
    }
    //----------------------------------

    /**
     * 게시글 작성 (생성메서드)
     */
    public static Article createArticle(User user, String subject, String contents, Category category){
        Article article = new Article();
        article.writeArticle(user, subject, contents, category);
        return article;
    }
    /**
     * 조회수 업데이트
     */
    public void addViewCount(){
        this.viewCount++;
    }

    /**
     * 게시글 작성
     */
    public void writeArticle(User user, String subject, String contents, Category category){
        this.user = user;
        this.subject = subject;
        this.contents = contents;
        this.category = category;
    }
    /**
     * 게시글 수정
     */
    public void editArticle(String subject, String contents, Category category){
        this.subject = subject;
        this.contents = contents;
        this.category = category;
    }

    /**
     * 게시글 삭제
     */
    public void deleteArticle() {
        this.flag = "D";
    }
}
