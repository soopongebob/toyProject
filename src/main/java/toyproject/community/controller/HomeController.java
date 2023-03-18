package toyproject.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import toyproject.community.domain.Article;
import toyproject.community.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ArticleService articleService;

    /**
     * 메인페이지
     * @param model : Article
     * @param pageable
     * @return index
     */
    @RequestMapping("/")
    public String home(Model model, HttpServletRequest request,
                       @PageableDefault(page = 0, size = 5, sort="articleIdx", direction = Sort.Direction.DESC)
                               Pageable pageable){
        HttpSession session = request.getSession();
        System.out.println("session = " + session.getAttribute("userName"));
        Page<Article> articles = articleService.getList(pageable);
        Page<ArticleListForm> articleList = new ArticleListForm().toList(articles);

        model.addAttribute("articles", articleList);
        return "index";
    }

    @RequestMapping("/map")
    public String map(){
        return "article/map";
    }
}

