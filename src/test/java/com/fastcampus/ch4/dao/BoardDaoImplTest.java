package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDaoImplTest {
    @Autowired
    BoardDao boardDao;

    @Test
    public void searchSelectPageTest() throws Exception {
        boardDao.deleteAll();

        for (int i = 1; i <= 20; i++) {
            BoardDto boardDto = new BoardDto("title" + i, "content" + i, "asdf" + i);
            boardDao.insert(boardDto);
        }

        SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
        List<BoardDto> list = boardDao.searchSelectPage(sc);

        for (BoardDto dto: list) {
            System.out.println("dto = " + dto);
        }

        assertTrue(list.size() == 2);   // 1~20,    title2, title20

        sc = new SearchCondition(1, 10, "asdf2", "W");
        list = boardDao.searchSelectPage(sc);

        for (BoardDto dto: list) {
            System.out.println("dto = " + dto);
        }

        assertTrue(list.size() == 2);   // 1~20,    asdf2, asdf20

    }

    @Test
    public void searchSelectCtnTset() throws Exception {
        boardDao.deleteAll();

        for (int i = 1; i <= 20; i++) {
            BoardDto boardDto = new BoardDto("title" + i, "content" + i, "asdf" + i);
            boardDao.insert(boardDto);
        }

        SearchCondition sc = new SearchCondition(1, 10, "title2", "T");
        int cnt = boardDao.searchResultCnt(sc);
        System.out.println("cnt = " + cnt);
        assertTrue(cnt == 2);

        sc = new SearchCondition(1, 10, "asdf2", "W");
        cnt = boardDao.searchResultCnt(sc);
        System.out.println("cnt = " + cnt);
        assertTrue(cnt == 2);

        sc = new SearchCondition(1, 10, "asdf2", "TW");
        cnt = boardDao.searchResultCnt(sc);
        System.out.println("cnt = " + cnt);
        assertTrue(cnt == 2);
    }

    @Test
    public void select() {
        try {
            BoardDto boardDto = boardDao.select(6);
            System.out.println(boardDto.getBno());
            assertTrue(boardDto.getBno().equals(6));
            System.out.println("boardDto = " + boardDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void count() {
        try {
            System.out.println("boardDao.count() = " + boardDao.count());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void insert() {
        BoardDto boardDto = new BoardDto("HI 수진", "HIHI", "asdf");
        try {

            for (int i = 100; i < 220; i++) {
                boardDao.insert(boardDto);
                boardDto = new BoardDto("HI 수진" + i, "HIHI" + i, "장수진" + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        int result = 0;
        try {
            result = boardDao.delete(2, "going");
            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void update() {
        BoardDto boardDto = new BoardDto("noooow", "not noooow", "장수진98");
        boardDto.setBno(6);
        int result = 0;
        try {
            result = boardDao.update(boardDto);
            System.out.println("result = " + result);
            Integer bno = boardDao.selectAll().get(0).getBno();
            System.out.println("bno = " + bno);
            boardDto.setBno(bno);
            boardDto.setTitle("Yes Title");
            assertTrue(boardDao.update(boardDto) == 1);

            BoardDto boardDto2 = boardDao.select(bno);
            System.out.println("boardDto = " + boardDto);
            System.out.println("boardDto2 = " + boardDto2);
            assertTrue(boardDto.equals(boardDto2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void selectAll() {
        try {
            List<BoardDto> dtos = boardDao.selectAll();
            for (BoardDto dto:dtos) {
                System.out.println("dto = " + dto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void increaseViewCnt() {
        int result = 0;
        try {
            result = boardDao.increaseViewCnt(5);
            System.out.println("result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteAll() {
        try {
            int result = boardDao.deleteAll();
            System.out.println("result = " + result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}