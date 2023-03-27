package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    BoardDto select(Integer bno) throws Exception;

    int count() throws Exception;

    int insert(BoardDto boardDto) throws Exception;

    int delete(Integer bno, String writer) throws Exception;

    int update(BoardDto boardDto) throws Exception;

    List<BoardDto> selectAll() throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;

    int deleteAll() throws Exception;

    List<BoardDto> selectPage(Map map) throws Exception;


    int searchResultCnt(SearchCondition searchCondition) throws Exception;

    List<BoardDto> searchSelectPage(SearchCondition searchCondition) throws Exception;
}
