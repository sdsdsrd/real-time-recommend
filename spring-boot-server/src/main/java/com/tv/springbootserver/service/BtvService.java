package com.tv.springbootserver.service;

import com.tv.springbootserver.dao.BtvDao;
import com.tv.springbootserver.dto.LogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BtvService {

    @Autowired
    private BtvDao dao;

    public List<LogDto> getLog() {
        return dao.getLog();
    }
}
