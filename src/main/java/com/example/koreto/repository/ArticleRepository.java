package com.example.koreto.repository;

import com.example.koreto.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article, Long> {


    List<Article> findAllByTitleContains(String query);

   List<Article> findAlByOrderByCreatedDateDesc();

    List<Article> findAllByOrderByCreatedDateAsc();

}
