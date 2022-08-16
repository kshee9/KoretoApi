package com.example.koreto.model;

import com.example.koreto.dto.ArticleResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private int view_count;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "attachment_id")
    private List<Attachment> attachmentList;

    //생성때 저장용
    public Article(ArticleResponseDto articleResponseDto) {
        this.title = articleResponseDto.getTitle();
        this.content = articleResponseDto.getContent();
    }

    //수정때 쓰기
    @Builder
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }


}
