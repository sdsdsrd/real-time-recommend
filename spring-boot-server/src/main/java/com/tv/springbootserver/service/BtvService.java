package com.tv.springbootserver.service;

import com.tv.springbootserver.dao.BtvDao;
import com.tv.springbootserver.dto.LogDto;
import com.tv.springbootserver.dto.TopGenreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BtvService {

    @Autowired
    private BtvDao dao;

    public List<LogDto> getLog() {
        return dao.getLog();
    }

    public List<TopGenreDto> getTopGenre(int stbId, String now) {
        Map<String, Object> map = new HashMap<>();
        getTimeZone(map, stbId, now);

        return dao.getTopGenre(map);
    }

    private void getTimeZone(Map<String, Object> map, int stbId, String now) {
        map.put("stbId", stbId);
        String oneWeekAgoFrom = "";
        String oneWeekAgoTo = "";
        String twoWeekAgoFrom = "";
        String twoWeekAgoTo = "";
        String time = now.substring(0, 14);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date date = dateFormat.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            calendar.add(Calendar.HOUR_OF_DAY, -1);
            oneWeekAgoFrom = dateFormat.format(calendar.getTime());
            map.put("oneWeekAgoFrom", oneWeekAgoFrom);
            calendar.add(Calendar.HOUR_OF_DAY, 2);
            oneWeekAgoTo = dateFormat.format(calendar.getTime());
            map.put("oneWeekAgoTo", oneWeekAgoTo);
            calendar.add(Calendar.DAY_OF_WEEK, -7);
            twoWeekAgoTo = dateFormat.format(calendar.getTime());
            map.put("twoWeekAgoTo", twoWeekAgoTo);
            calendar.add(Calendar.HOUR_OF_DAY, -2);
            twoWeekAgoFrom = dateFormat.format(calendar.getTime());
            map.put("twoWeekAgoFrom", twoWeekAgoFrom);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
