package com.tv.springbootserver.dao;

import com.tv.springbootserver.dto.LogDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BtvDao {
    List<LogDto> getLog();
}
