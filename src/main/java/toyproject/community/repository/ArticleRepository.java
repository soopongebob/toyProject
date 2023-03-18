package toyproject.community.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import toyproject.community.domain.Article;
import toyproject.community.domain.Category;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAll(Pageable pageable);
    Article findByArticleIdx(Long articleIdx);
    void deleteByArticleIdx(Long articleIdx);
    Page<Article> findByCategory(Pageable pageable, Category category);

    @Query(value="SELECT a FROM Article a WHERE a.subject LIKE %:search% OR a.contents LIKE %:search%")
    Page<Article> searchQuery(@Param("search") String searchSubject, Pageable pageable);
//    Page<ArticleListForm> searchPost(SearchCondition searchCondition, Pageable pageable);
}
