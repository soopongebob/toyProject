package toyproject.community.controller;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import toyproject.community.domain.Article;
import toyproject.community.domain.Category;
import toyproject.community.domain.User;
import toyproject.community.service.ArticleService;
import toyproject.community.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    /**
     * articleList_ALL
     * @param model
     * @param pageable
     * @param request
     * @return
     */
    @GetMapping("/article/list")
    public String articleList(Model model,
                              @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
        Page<Article> articles = articleService.getList(pageable);
        Page<ArticleListForm> articleList = new ArticleListForm().toList(articles);
        //write 버튼 hide
        String auth = "false";
        if (request.getSession().getAttribute("userIdx") != null) {
            auth = "true";
        }
        model.addAttribute("Auth", auth);
        model.addAttribute("articles", articleList);
        return "article/list";
    }

    @GetMapping("/article/list/{category}")
    public String articleList_category(Model model,
                              @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request, @PathVariable("category") String category) {
        Page<Article> articles = articleService.getList_category(pageable, category);
        Page<ArticleListForm> articleList = new ArticleListForm().toList(articles);
        //write 버튼 hide
        String auth = "false";
        if (request.getSession().getAttribute("userIdx") != null) {
            auth = "true";
        }
        model.addAttribute("Auth", auth);
        model.addAttribute("articles", articleList);
        return "article/list";
    }

    /**
     * 글쓰기
     */
    @GetMapping("/article/write")
    public String articleWrite(Model model) {
        model.addAttribute("articleWriteForm", new ArticleWriteForm());
        return "article/write";
    }

    @PostMapping("/article/write")
    public String articleWriteInsert(@ModelAttribute @Valid ArticleWriteForm articleWriteForm, HttpServletRequest request) {
        Category category = articleWriteForm.getCategory();
        String subject = articleWriteForm.getSubject();
        String content = articleWriteForm.getContent();
        HttpSession session = request.getSession();
        String userId = session.getAttribute("userId").toString();
        Long idx = articleService.write(userId, category, subject, content);
        return "redirect:/article/view/" + idx;
    }

    /**
     * 글 조회
     */
    @GetMapping("/article/view/{idx}")
    public String viewArticle(@PathVariable("idx") Long articleIdx, Model model, HttpServletRequest request){
        Article article = articleService.getArticle(articleIdx);
        String createDtm = article.getCreatedDTM().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        ArticleViewForm articleViewForm = new ArticleViewForm(articleIdx, article.getSubject(), article.getUser().getUserName(),article.getContents(), article.getCategory(), createDtm, article.getViewCount().longValue(), article.getComments());
        //댓글추가
        model.addAttribute("comments", articleViewForm.getComments());
        model.addAttribute("article", articleViewForm);
        model.addAttribute("comment", new CommentForm());

        //권한확인
        HttpSession session = request.getSession();
        String auth = "false";
        String commentAuth = "false";
        String articleAuth = "false";

        if (session.getAttribute("userIdx") != null) {
            auth = "true";  //로그인유저 권한
            String loginUserIdx = session.getAttribute("userIdx").toString();
            commentAuth = loginUserIdx;
            System.out.println("loginUserIdx = " + loginUserIdx);
            String writeUserIdx = article.getUser().getUserIdx().toString();
            System.out.println("writeUserIdx = " + writeUserIdx);
            if(writeUserIdx.equals(loginUserIdx)){
                articleAuth = "true";   //내 글 권한
            }
        }
        model.addAttribute("Auth", auth);
        model.addAttribute("articleAuth", articleAuth);
        model.addAttribute("commentAuth", commentAuth);
        return "article/view";
    }

    /**
     * 글 수정
     */
    @GetMapping("/article/edit/{idx}")
    public String editArticleForm(@PathVariable("idx") Long articleIdx, Model model){
        Article article = articleService.getArticle(articleIdx);
        model.addAttribute("articleEditForm", new ArticleEditForm(articleIdx, article.getSubject(), article.getContents(), article.getCategory()));
        return "article/edit";
    }

    @PostMapping("/article/edit/{idx}")
    public String editArticle(@PathVariable("idx") Long articleIdx, ArticleEditForm articleEditForm){
        Article article = articleService.editArticle(articleIdx, articleEditForm.getCategory(), articleEditForm.getSubject(), articleEditForm.getContent());
        return "redirect:/article/view/" + article.getArticleIdx();
    }

    /**
     * 글 삭제
     */
    @GetMapping("/article/delete/{idx}")
    public String deleteArticle(@PathVariable("idx") Long articleIdx, Model model){
        articleService.deleteArticle(articleIdx);
        System.out.println("삭제맨");
        model.addAttribute("msg", "게시글이 삭제되었습니다.");
        return "redirect:/article/list";
    }
    /**
     * 이미지파일 업로드
     */
    @PostMapping(value = "/article/uploadedImage", produces = "application/json")
    @ResponseBody
    public JsonObject SummerNoteImageFile(@RequestParam("file") MultipartFile file) {
        JsonObject jsonObject = new JsonObject();
        String fileRoot = "C:\\summernote_image\\";
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String savedFileName = UUID.randomUUID() + extension; //저장할 파일명
        File targetFile = new File(fileRoot + savedFileName);

        try{
            InputStream fileStream = file.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);

            jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode", "success");
        } catch (IOException e){
            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * 검색
     */
    @GetMapping("/article/search")
    public String search(@RequestParam(value = "search", required = false) String search, Model model,
                         @PageableDefault(size = 10) Pageable pageable, HttpServletRequest request) {
        Page<Article> articles = articleService.getSearchResult(search,pageable);
        Page<ArticleListForm> articleList = new ArticleListForm().toList(articles);
        model.addAttribute("articles", articleList);
        return "/article/searchResult";
    }
}
