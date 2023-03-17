package toyproject.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toyproject.community.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByCommentIdx(Long commentIdx);
    void deleteByCommentIdx(Long commentIdx);
}
