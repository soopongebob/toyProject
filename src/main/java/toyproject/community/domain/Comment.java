package toyproject.community.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Comment extends BaseDTM{
    @Id @GeneratedValue
    private Long commentIdx;
    @NotBlank
    private String comment;
    private String flag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "articleIdx")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

    //연관관계 메서드-------------------
    public void setArticle(Article article) {
        this.article = article;
        article.getComments().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.getComments().add(this);
    }
    //-------------------------------


    /**
     * 댓글생성
     */
    public void writeComment(String comment){
        this.comment = comment;
    }

    /**
     * 댓글수정
     */
    public void editComment(String comment) {
        this.comment = comment;
    }

    /**
     * 댓글삭제
     */
    public void deleteComment() {
        this.flag = "D";
    }

    //@ToString(of = {"commentIdx","comment", "flag", "User.userName", "User.userIdx", "articleIdx"})

    @Override
    public String toString(){
        return "Comment(commentIdx=" + commentIdx + ", "
                + "comment=" + comment + ", "
                + "flag=" + flag + ", "
                + "articleIdx=" + article.getArticleIdx() + ", "
                + "articleSubject=" + article.getSubject() + ")";
    }
}
