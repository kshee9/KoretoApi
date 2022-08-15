package com.example.koreto.controller;

import com.example.koreto.dto.ArticleRequestDto;
import com.example.koreto.dto.BoardRequestDto;
import com.example.koreto.dto.ResponseDto;
import com.example.koreto.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {


    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService){
        this.articleService = articleService;
    }


    //게시판 생성
    @PostMapping("/api/createBoard")
    public ResponseDto<?> createBoard(@RequestBody BoardRequestDto boardRequestDto){
        return articleService.createBoard(boardRequestDto);
    }

    //게시글 생성
    @PostMapping("/api/createPost")
    public ResponseDto<?> createPost(@RequestBody  ArticleRequestDto articleRequestDto){
        return articleService.createPost(articleRequestDto);
    }

    //게시글 수정
    @PutMapping("/api/post/{articleid}")
    public ResponseDto<?> updatePost(@RequestBody ArticleRequestDto articleRequestDto, @PathVariable Long articleid){
       return articleService.updatePost(articleRequestDto,articleid);
    }

    //게시글 삭제
    @DeleteMapping("/api/post/{articleid}")
    public ResponseDto<?> deleterPost(@PathVariable Long articleid){
        return articleService.deletePost(articleid);
    }

    // 시작날짜로 조히
    @GetMapping("api/posts/createtime")
    public ResponseDto<?> getPostByCreateDate(){
        return articleService.getPostByCreateDate();
    }

    // 끝나는날짜로 조히
    @GetMapping("api/posts/endtime/")
    public ResponseDto<?> getPostByEndDate(){
        return articleService.getPostByCreateDate();
    }

    // 이름으로 조회
    @GetMapping("api/posts/{query}")
    public ResponseDto<?> getPostByBoardName(@PathVariable String query){
        return articleService.getPostByBoardName(query);
    }

}
