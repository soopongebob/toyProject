package toyproject.community.controller;

import lombok.Data;
import org.springframework.data.domain.Page;
import toyproject.community.domain.Article;
import toyproject.community.domain.Category;

import java.time.LocalDateTime;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
public class ArticleListForm {
    private Long articleIdx;
    private String subject;
    private String contents;
    private String writer;
    private Long viewCount;
    private LocalDateTime createDTM;
    private LocalDateTime editDTM;
    private Category category;

    public Page<ArticleListForm> toList(Page<Article> articles){
        Page<ArticleListForm> articleList = articles.map(m -> new ArticleListForm(
                m.getArticleIdx(),
                m.getSubject(),
                m.getContents(),
                m.getUser().getUserName(),
                m.getViewCount(),
                m.getCreatedDTM(),
                m.getModifiedDTM(),
                m.getCategory()
        ));
        return articleList;
    }

    public ArticleListForm() {
    }

    public ArticleListForm(Long articleIdx, String subject, String contents, String writer, Long viewCount, LocalDateTime createDTM, LocalDateTime editDTM, Category category) {
        this.articleIdx = articleIdx;
        this.subject = subject;
        this.contents = contents;
        this.writer = writer;
        this.viewCount = viewCount;
        this.createDTM = createDTM;
        this.editDTM = editDTM;
        this.category = category;
    }
}
