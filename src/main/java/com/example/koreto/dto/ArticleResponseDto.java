package com.example.koreto.dto;

import com.example.koreto.model.Article;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDto {

    private  String title;

    private  String content;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime createdDate;

    private int view_count;

//    @Builder
//    public ArticleResponseDto(String title, String content, Timestamp created_datetime){
//        this.title = title;
//        this.content = content;
//        this.created_datetime = created_datetime;
//    }

    public ArticleResponseDto(Article article){
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdDate = article.getCreatedDate();
    }

}

