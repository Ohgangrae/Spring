package com.example.board.controller;
/*
* REST(Representational State Transfer)
*       데이터 전송 상태 즉 url이 하나의 정보이다
*       하나의 URI는 하나의 고유한 리소스(데이터)를 대표하도록 설계된다.
*       예)/board/123 : 게시글의 전체 정보
* */

import com.example.board.domain.vo.ReplyVO;
import com.example.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@Slf4j
@RequestMapping("/reply/*")
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    //왜쓰지?
    //댓글 등록
    //브라우저에서 JSON타입으로 데이터를 전송하고 서버에서는 댓글의 처리 결과에 따라 문자열로 결과를 리턴한다.
    //consumes : 전달받은 데이터의 타입
    //produces : 콜백함수로 결과를 전달할 때의 타입
    //ResponseEntity : 서버의 상태코드, 응답 메세지 등을 담을 수 있는 타입
    //@RequestBody : 전달받은 데이터를 알맞는 매개변수로 주입할 때 사용
    @PostMapping(value = "/new", consumes = "application/json", produces = "text/plain; charset=utf-8")
    public ResponseEntity<String> create(@RequestBody ReplyVO replyVO) throws UnsupportedEncodingException{
        log.info("replyVO : " + replyVO);
        replyService.register(replyVO);
        return new ResponseEntity<>(new String("댓글 등록 성공".getBytes(), "UTF-8") ,HttpStatus.OK)
    }

}
