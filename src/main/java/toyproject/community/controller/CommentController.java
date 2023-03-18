package toyproject.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyproject.community.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     */
//    @PostMapping("/article/comment/{idx}")
//    public String writeComment(@PathVariable("idx") Long articleIdx, CommentForm commentForm, HttpServletRequest request){
//        String userId = request.getSession().getAttribute("userId").toString();
//        commentForm.setArticleIdx(articleIdx);
//        commentForm.setUserId(userId);
//        commentService.writeComment(commentForm);
//
//        return "redirect:/article/view/" + articleIdx;
//    }
    @PostMapping("/article/comment/{idx}")
    public String writeComment(@PathVariable("idx") Long articleIdx,@RequestBody Map<String, Object> paramMap, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        CommentForm commentForm = new CommentForm();

        String comment = paramMap.get("comment").toString();

        commentForm.setComment(comment);
        commentForm.setArticleIdx(articleIdx);
        commentForm.setUserId(userId);
        commentService.writeComment(commentForm);

        return "redirect:/article/view/" + articleIdx;
    }

    /**
     * 댓글 수정
     */
    @PostMapping("/article/comment/edit")
    public String editComment(HttpServletRequest request, @RequestBody Map<String, Object> paramMap){

        HttpSession session = request.getSession();
        String userId = session.getAttribute("userId").toString();
        CommentForm commentForm = new CommentForm();

        Long commentIdx = Long.parseLong(paramMap.get("commentIdx").toString());
        System.out.println("commentIdx = " + commentIdx);
        String comment = paramMap.get("comment").toString();
        System.out.println("comment = " + comment);
        Long articleIdx = Long.parseLong(paramMap.get("articleIdx").toString());
        System.out.println("articleIdx = " + articleIdx);

        commentForm.setCommentIdx(commentIdx);
        commentForm.setComment(comment);
        commentForm.setArticleIdx(articleIdx);
        commentForm.setUserId(userId);

        commentService.editComment(commentForm);

        return "redirect:/article/view/" + articleIdx;
    }

    /**
     * 댓글 수정
     */
    @PostMapping("/article/comment/delete")
    public String deleteComment(HttpServletRequest request, @RequestBody Map<String, Object> paramMap){
        HttpSession session = request.getSession();
        String userId = session.getAttribute("userId").toString();
        CommentForm commentForm = new CommentForm();

        Long commentIdx = Long.parseLong(paramMap.get("commentIdx").toString());
        Long articleIdx = Long.parseLong(paramMap.get("articleIdx").toString());

        commentForm.setCommentIdx(commentIdx);
        commentForm.setArticleIdx(articleIdx);
        commentForm.setUserId(userId);

        commentService.deleteComment(commentForm);

        return "redirect:/article/view/" + articleIdx;
    }

}
