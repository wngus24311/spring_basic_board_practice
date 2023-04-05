package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@ResponseBody
public class CommentController {

    @Autowired
    CommentService service;

    // 지정된 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
            System.out.println("list = " + list);
            return new ResponseEntity<>(list, HttpStatus.OK);    // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);   // 400
        }
    }

    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}")   // /comments/1?bno=1085 <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
        String commenter = "asdf";
//        String commenter = (String) session.getAttribute("id");

        try {
            int rowCnt = service.remove(cno, bno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }
    }

//    {
//        "pcno":0,
//            "comment" : "hi"
//    }
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto commentDto, Integer bno, HttpSession session) {
//        String commenter = (String) session.getAttribute("id");
        String commenter = "asdf";
//        CommentDto commentDto = new CommentDto(bno, pcno, commenter, comment);
        System.out.println("commenter = " + commenter);
        System.out.println("commentDto = " + commentDto);
        commentDto.setBno(bno);
        commentDto.setCommenter(commenter);
        try {
            int rowCnt = service.write(commentDto);

            if (rowCnt != 1) {
                throw new Exception("Write Failed");
            }
            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }

    }

//    {
//        "pcno":0,
//        "comment" : "gggg3",
//        "commenter" : "asdf"
//    }
    @PatchMapping("/comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto commentDto, HttpSession session) {
//        String commenter = (String) session.getAttribute("id");
        String commenter = "asdf";
        commentDto.setCno(cno);
        commentDto.setCommenter(commenter);
        System.out.println("commentDto = " + commentDto);

        try {
            if (service.modify(commentDto) != 1)
                throw new Exception("Modify Failed");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }

    }

}
