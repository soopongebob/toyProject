package toyproject.community.controller;

import lombok.Data;

@Data
public class CommentForm {
    private Long commentIdx;
    private String comment;
    private Long articleIdx;
    private String userId;

    public CommentForm() {
    }

    public CommentForm(Long commentIdx, String comment, Long articleIdx, String userId) {
        this.commentIdx = commentIdx;
        this.comment = comment;
        this.articleIdx = articleIdx;
        this.userId = userId;
    }
}
