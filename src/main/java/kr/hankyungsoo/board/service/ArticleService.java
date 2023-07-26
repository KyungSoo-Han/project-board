package kr.hankyungsoo.board.service;

import kr.hankyungsoo.board.domain.Article;
import kr.hankyungsoo.board.domain.type.SearchType;
import kr.hankyungsoo.board.dto.ArticleDto;
import kr.hankyungsoo.board.dto.ArticleWithCommentsDto;
import kr.hankyungsoo.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword==null|| searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(entity -> ArticleDto.from(entity));
        }

        return switch (searchType){
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(article -> ArticleDto.from(article));
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(article -> ArticleDto.from(article));
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(article -> ArticleDto.from(article));
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(article -> ArticleDto.from(article));
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(article -> ArticleDto.from(article));
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map((Article entity) -> ArticleWithCommentsDto.from(entity))
                .orElseThrow(()->new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());
            if (dto.title() != null) {
                article.setTitle(dto.title());
            }
            if (dto.content() != null) {
                article.setContent(dto.content());
            }
            //if(dto.hashtag() != null) {article.setHashtag(dto.hashtag());}
            article.setHashtag(dto.hashtag());
        }
        catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}
