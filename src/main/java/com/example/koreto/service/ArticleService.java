package com.example.koreto.service;

import com.example.koreto.dto.ArticleRequestDto;
import com.example.koreto.dto.ArticleResponseDto;
import com.example.koreto.dto.BoardRequestDto;
import com.example.koreto.dto.ResponseDto;
import com.example.koreto.model.Article;
import com.example.koreto.model.Attachment;
import com.example.koreto.model.Board;
import com.example.koreto.repository.ArticleRepository;
import com.example.koreto.repository.AttachmentRepository;
import com.example.koreto.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class ArticleService {

//    @Value("${uploadpath}")
//    private  String path;

    private final ArticleRepository articleRepository;
    private final AttachmentRepository attachmentRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository,
                          AttachmentRepository attachmentRepository,
                          BoardRepository boardRepository){
        this.articleRepository = articleRepository;
        this.attachmentRepository = attachmentRepository;
        this.boardRepository = boardRepository;
    }


    public void createMockFile(){


    }
    //게시판 생성
    @Transactional
    public ResponseDto<?> createBoard(BoardRequestDto boardRequestDto){
        Board board  = Board.builder()
                .name(boardRequestDto.getName())
                .build();

        boardRepository.save(board);

        return new ResponseDto<>("성공");
    }

    //게시글 생성
    @Transactional
    public ResponseDto<?> createPost(ArticleRequestDto articleRequestDto) {


//        String OriginalFileName = file.getOriginalFilename();
//        //파일 이름 따오기
//        String fileName = OriginalFileName.substring(OriginalFileName.lastIndexOf("//"+1));
//
//
//        //파일 지정한 경로에 저장
//        file.transferTo(new File(path+fileName));

        List<Attachment> attachments  = new LinkedList<>();

        for (int k = 0; k<3 ; k++) {
            String path= String.valueOf(UUID.randomUUID());

            Attachment attachment = Attachment.builder()
                    .location(path)
                    .build();

            attachments.add(attachment);
            attachmentRepository.save(attachment);
        }

//        // 엔티티 저장
//        List<Attachment> allattachment = attachmentRepository.saveAll(attachments);


        ArticleResponseDto articleResponseDto =  ArticleResponseDto.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .createdDate(articleRequestDto.getCreatedDate())
                .build();

        log.info("제목 : " + articleRequestDto.getTitle());
        log.info("내용 : " + articleRequestDto.getContent());

        Article article = new Article(articleResponseDto);

        articleRepository.save(article);


        return new ResponseDto<>("성공", articleResponseDto);
    }

    //게시글 수정
    public  ResponseDto<?> updatePost(ArticleRequestDto articleRequestDto, Long articleid){

        Article article = articleRepository.findById(articleid).orElseThrow(
                ()-> new NullPointerException("없는 게시글입니다.")
        );
        Article newarticle = Article.builder()
                .content(articleRequestDto.getContent())
                .title(articleRequestDto.getTitle())
                .build();

        if (article.getContent() == newarticle.getContent()){
            throw new RuntimeException("중복되는 내용입니다.");
        }
        else if (article.getTitle() == newarticle.getTitle()){
            throw  new RuntimeException("중복되는 제목입니다");
        }
        articleRepository.save(newarticle);

        return  new ResponseDto<>("성공");
    }
    //게시글 조회
    public  ResponseDto<?> getDetailPost(Long articleid){

        Article article = articleRepository.findById(articleid).orElseThrow(
                ()-> new NullPointerException("없는 게시글입니다.")
        );

        int sum = article.getView_count();
        sum +=1;

        ArticleResponseDto resDto = ArticleResponseDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .createdDate(article.getCreatedDate())
                .view_count(sum)
                .build();

        return  new ResponseDto<>("완료",resDto );

    }

    //게시글 삭제
    @Transactional
    public ResponseDto<?>  deletePost(Long articleid){
        Article article = articleRepository.findById(articleid).orElseThrow(
                ()-> new IllegalArgumentException("오류")
        );

        articleRepository.delete(article);

        return new ResponseDto<Object>("성공");
    }

    //시작날짜순 게시글 불러오기
    public  ResponseDto<?> getPostByCreateDate(){

        List<Article> articles = articleRepository.findAlByOrderByCreatedDateDesc();

        List<ArticleResponseDto> resultDto = new ArrayList<>();

        for(int i = 0; i < articles.size(); i++){
            ArticleResponseDto articleResponseDto = new ArticleResponseDto(articles.get(i));
            resultDto.add(articleResponseDto);
        }

        return new ResponseDto<>("성공",resultDto);
    }

    //끝나는 날짜순 게시글 불러오기
    public  ResponseDto<?> getPostByEndDate(){


        List<Article> articles = articleRepository.findAllByOrderByCreatedDateAsc();

        List<ArticleResponseDto> resultDto = new ArrayList<>();

        for(int i = 0; i < articles.size(); i++){
            ArticleResponseDto articleResponseDto = new ArticleResponseDto(articles.get(i));
            resultDto.add(articleResponseDto);
        }

        return new ResponseDto<>("성공",resultDto);
    }



    //이름 게시글 불러오기
    public  ResponseDto<?> getPostByBoardName( String query){

        List<Article> articles = articleRepository.findAllByTitleContains(query);

        List<ArticleResponseDto> resultDto = new ArrayList<>();

        for(int i = 0; i < articles.size(); i++){
            ArticleResponseDto articleResponseDto = new ArticleResponseDto(articles.get(i));
            resultDto.add(articleResponseDto);
        }

        return new ResponseDto<>("성공",resultDto);
    }
}
