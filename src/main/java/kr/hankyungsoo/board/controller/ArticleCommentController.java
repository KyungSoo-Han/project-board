package kr.hankyungsoo.board.controller;

import kr.hankyungsoo.board.domain.UserAccount;
import kr.hankyungsoo.board.dto.UserAccountDto;
import kr.hankyungsoo.board.dto.request.ArticleCommentRequest;
import kr.hankyungsoo.board.dto.request.ArticleRequest;
import kr.hankyungsoo.board.service.ArticleCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class ArticleCommentController {
    private final ArticleCommentService articleCommentService;

    @PostMapping("/new")
    public String postNewArticleComment(ArticleCommentRequest articleCommentRequest){
        // TODO: 인증 정보를 넣어줘야 한다.
        articleCommentService.saveArticleComment(articleCommentRequest.toDto(UserAccountDto.of(
                "hanks","1234", "hanks@email.com","hanks", "memo"
        )));

        return "redirect:/articles/"+articleCommentRequest.articleId();
    }

    @PostMapping("/{commentId}/delete")
    public String deleteArticleComment(@PathVariable Long commentId, Long articleId){
        articleCommentService.deleteArticleComment(commentId);

        return "redirect:/articles/" +articleId;
    }

}
