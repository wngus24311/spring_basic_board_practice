package com.fastcampus.ch4.service;

import com.fastcampus.ch4.dao.BoardDao;
import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardDao boardDao;

    @Override
    public List<BoardDto> getSearchResultPage(SearchCondition searchCondition) throws Exception {
        return boardDao.searchSelectPage(searchCondition);
    }

    @Override
    public int getSearchResultCount(SearchCondition searchCondition) throws Exception {
        return boardDao.searchResultCnt(searchCondition);
    }

    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    }

    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }

    @Override
    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    }

    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }

    @Override
    public BoardDto read(Integer bno) throws Exception {
        BoardDto dto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);
        return dto;
    }

    @Override
    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }

    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    }

}
