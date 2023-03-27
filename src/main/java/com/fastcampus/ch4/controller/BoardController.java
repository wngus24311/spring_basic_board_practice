package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model model) {
        try {
            BoardDto boardDto = boardService.read(bno);
//            model.addAttribute("boardDto", boardDto); // 아래 문장과 동일
            model.addAttribute(boardDto);
            model.addAttribute("page", page);
            model.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "board";
    }

    @GetMapping("/list")
    public String list(@ModelAttribute SearchCondition searchCondition, Model model, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

        try {
            int totalCnt = boardService.getCount();
            PageHandler pageHandler = new PageHandler(totalCnt, searchCondition);

            List<BoardDto> list = boardService.getSearchResultPage(searchCondition);
            model.addAttribute("totalCnt", totalCnt);
            model.addAttribute("list", list);
            model.addAttribute("ph", pageHandler);

            System.out.println("controller ========> totalCnt = " + totalCnt);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            model.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model model, HttpSession session, RedirectAttributes rattr) {
        String writer = (String) session.getAttribute("id");
        try {
            int rowCnt = boardService.remove(bno, writer);
            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);

            if (rowCnt == 1) {
                rattr.addFlashAttribute("msg", "Complete");
                return "redirect:/board/list";
            } else {
                throw new Exception("board remove error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "Error");

        }

        return "redirect:/board/list";
    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("mode", "new");
        return "board"; // 읽기와 쓰기에 사용, 쓰기에 사용할때는 new
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, HttpSession session, Model model, RedirectAttributes rattr) {
        String write = (String) session.getAttribute("id");
        boardDto.setWriter(write);

        try {
            int rowCnt = boardService.write(boardDto);

            if (rowCnt != 1) {
                throw new Exception("Write failed");
            }
            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("msg", "WRT_ERR");
            return "board";
        }
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, HttpSession session, Model model, RedirectAttributes rattr,
    Integer page, Integer pageSize) {
        String write = (String) session.getAttribute("id");
        boardDto.setWriter(write);

        try {
            int rowCnt = boardService.modify(boardDto);

            if (rowCnt != 1) {
                throw new Exception("Modify failed");
            }
            rattr.addAttribute("page", page);
            rattr.addAttribute("pageSize", pageSize);
            rattr.addFlashAttribute("msg", "MOD_OK");

            return "redirect:/board/list";
//            return "redirect:/board/list?page=" + page + "&pageSize=" + pageSize;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("boardDto", boardDto);
            model.addAttribute("msg", "MOD_ERR");
            return "board";
        }
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}