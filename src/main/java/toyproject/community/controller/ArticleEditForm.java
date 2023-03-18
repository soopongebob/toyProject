package toyproject.community.controller;

import lombok.Data;
import toyproject.community.domain.Category;

@Data
public class ArticleEditForm {
    private Long articleIdx;
    private String subject;
    private String content;
    private Category category;

    public ArticleEditForm() {
    }

    public ArticleEditForm(Long articleIdx, String subject, String content, Category category) {
        this.articleIdx = articleIdx;
        this.subject = subject;
        this.content = content;
        this.category = category;
    }
    public ArticleEditForm(String subject, String content, Category category) {
        this.subject = subject;
        this.content = content;
        this.category = category;
    }

}
