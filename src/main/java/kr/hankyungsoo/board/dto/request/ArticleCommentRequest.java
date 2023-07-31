package kr.hankyungsoo.board.dto.request;

import kr.hankyungsoo.board.dto.ArticleCommentDto;
import kr.hankyungsoo.board.dto.UserAccountDto;

import java.io.Serializable;

/**
 * DTO for {@link kr.hankyungsoo.board.domain.ArticleComment}
 */
public record ArticleCommentRequest(Long articleId, String content) {
    public static ArticleCommentRequest of(Long articleId, String content){
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto){
        return ArticleCommentDto.of(articleId, userAccountDto, content);
    }

}