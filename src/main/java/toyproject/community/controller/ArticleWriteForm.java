package toyproject.community.controller;

import lombok.Data;
import toyproject.community.domain.Category;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ArticleWriteForm {

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;
    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
