package com.tv.springbootserver.dao;

import com.tv.springbootserver.dto.LogDto;
import com.tv.springbootserver.dto.TopGenreDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BtvDao {
    List<LogDto> getLog();
    List<TopGenreDto> getTopGenre(Map<String, Object> paramMap);
    String getContentId(Map<String, Object> paramMap);
    List<String> getEpsdIdList(Map<String, Object> paramMap);
    List<String> getEpsdIdsFromContentId(String contentId);
}
