package kr.hankyungsoo.board.repository;

import kr.hankyungsoo.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}