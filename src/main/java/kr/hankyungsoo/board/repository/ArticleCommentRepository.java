package kr.hankyungsoo.board.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import kr.hankyungsoo.board.domain.ArticleComment;
import kr.hankyungsoo.board.domain.QArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){


        bindings.excludeUnlistedProperties(true);       // Article에 대한 검색을 선택적으로 구현하기 위함 //false => 모든 컬럼 검색 조건
        bindings.including(root.content, root.createdAt, root.createdBy);     // 원하는 검색 컬럼 추가
        //bindings.bind(root.content).first((stringPath, str) -> stringPath.likeIgnoreCase(str));     // like '${v}'
        bindings.bind(root.content).first((stringPath, str) -> stringPath.containsIgnoreCase(str));   // like '%${v}%'
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first((stringPath, str) -> stringPath.containsIgnoreCase(str));   // like '%${v}%'

    }
}
