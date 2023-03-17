package toyproject.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.community.controller.CommentForm;
import toyproject.community.domain.Comment;
import toyproject.community.repository.ArticleRepository;
import toyproject.community.repository.CommentRepository;
import toyproject.community.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void writeComment(CommentForm commentForm){
        Comment comment = new Comment();
        comment.writeComment(commentForm.getComment());
        comment.setArticle(articleRepository.findByArticleIdx(commentForm.getArticleIdx()));
        comment.setUser(userRepository.findByUserId(commentForm.getUserId()));
        commentRepository.save(comment);
    }

    @Transactional
    public void editComment(CommentForm commentForm){
        Comment comment = commentRepository.findByCommentIdx(commentForm.getCommentIdx());
        comment.editComment(commentForm.getComment());
    }

    @Transactional
    public void deleteComment(CommentForm commentForm){
        commentRepository.deleteByCommentIdx(commentForm.getCommentIdx());

    }
}
