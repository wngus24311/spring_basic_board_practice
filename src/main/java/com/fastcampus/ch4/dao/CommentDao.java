package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.CommentDto;

import java.util.List;

public interface CommentDao {
    int deleteAll(Integer bno) throws Exception;

    int count(Integer bno) throws Exception;

    int delete(Integer bno, String commenter) throws Exception;

    int insert(CommentDto commentDto) throws Exception;

    List<CommentDto> selectAll(Integer bno) throws Exception;

    CommentDto select(Integer cno) throws Exception;

    int update(CommentDto commentDto) throws Exception;
}
