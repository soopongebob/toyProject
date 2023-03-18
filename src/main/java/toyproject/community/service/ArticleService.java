package toyproject.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.community.controller.ArticleEditForm;
import toyproject.community.domain.Article;
import toyproject.community.domain.Category;
import toyproject.community.domain.User;
import toyproject.community.repository.ArticleRepository;
import toyproject.community.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public Page<Article> getList(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "articleIdx");
        return articleRepository.findAll(pageable);
    }

    public Page<Article> getList_category(Pageable pageable, String category){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "articleIdx");
        Category convCategory = Category.valueOf(category.toUpperCase());
        return articleRepository.findByCategory(pageable, convCategory);
    }

    public Page<Article> getSearchResult(String search, Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.Direction.DESC, "articleIdx");
        return articleRepository.searchQuery(search, pageable);
    }

    @Transactional
    public Long write(String userId, Category category, String subject, String content) {
        User writer = userRepository.findByUserId(userId);
        Article article = Article.createArticle(writer, subject, content, category);
        Article saveArticle = articleRepository.save(article);
        article.setUser(writer);
        return saveArticle.getArticleIdx();
    }

    @Transactional
    public Article getArticle(Long idx) {
        Article article = articleRepository.findByArticleIdx(idx);
        article.addViewCount();
        return article;
    }

    @Transactional
    public Article editArticle(Long idx, Category category, String subject, String content){
        Article article = articleRepository.findByArticleIdx(idx);
        article.editArticle(subject, content, category);
        return article;
    }

    @Transactional
    public void deleteArticle(Long idx){
        articleRepository.deleteByArticleIdx(idx);
    }

}
