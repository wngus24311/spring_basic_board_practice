package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.SearchCondition;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {
    SqlSession session;

    @Autowired
    public BoardDaoImpl(SqlSession session) {
        this.session = session;
    }
    private static String namespace = "com.fastcampus.ch4.dao.BoardMapper.";

    @Override
    public BoardDto select(Integer bno) throws Exception{
        return session.selectOne(namespace + "select", bno);
    }

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace + "count");
    }

    @Override
    public int insert(BoardDto boardDto) throws Exception {
        return session.insert(namespace + "insert", boardDto);
    }

    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap<>();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace + "delete", map);
    }

    @Override
    public int update(BoardDto boardDto) throws Exception {
        return session.update(namespace + "update", boardDto);
    }

    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace + "selectAll");
    }

    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace + "increaseViewCnt", bno);
    }

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace + "selectPage", map);
    }

    @Override
    public int searchResultCnt(SearchCondition searchCondition) throws Exception {
        return session.selectOne(namespace + "searchResultCnt", searchCondition);
    }

    @Override
    public List<BoardDto> searchSelectPage(SearchCondition searchCondition) throws Exception {
        return session.selectList(namespace + "searchSelectPage", searchCondition);
    }

    @Override
    public int updateCommentCnt(Integer bno, int cnt) {
        System.out.println("bno = " + bno);
        System.out.println("cnt = " + cnt);
        Map map = new HashMap();
        map.put("cnt", cnt);
        map.put("bno", bno);
        return session.update(namespace + "updateCommentCnt", map);
    }
}
