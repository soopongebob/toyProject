package toyproject.community.controller;

import lombok.Data;
import toyproject.community.domain.Category;
import toyproject.community.domain.Comment;

import java.util.List;

@Data
public class ArticleViewForm {

    private Long articleIdx;
    private String subject;
    private String writer;
    private String content;
    private Category category;
    private String createdDtm;
    private Long viewCount;
    private List<Comment> comments;

    public ArticleViewForm(Long articleIdx, String subject, String writer, String content, Category category, String createdDtm, Long viewCount, List<Comment> comments) {
        this.articleIdx = articleIdx;
        this.subject = subject;
        this.writer = writer;
        this.content = content;
        this.category = category;
        this.createdDtm = createdDtm;
        this.viewCount = viewCount;
        this.comments = comments;
    }

}
