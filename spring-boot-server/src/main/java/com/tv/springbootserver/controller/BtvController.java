package com.tv.springbootserver.controller;

import com.tv.springbootserver.dto.LogDto;
import com.tv.springbootserver.dto.TopGenreDto;
import com.tv.springbootserver.service.BtvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class BtvController {

    @Autowired
    private BtvService service;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/log")
    public List<LogDto> getLog() {
        return service.getLog();
    }

    @GetMapping("/genre/{stbId}/{now}")
    public List<TopGenreDto> getTopGenre(@PathVariable int stbId, @PathVariable String now) {
        return service.getTopGenre(stbId, now);
    }

    @GetMapping("/epsd/{stbId}/{now}")
    public List<String> getEpsdIdList(@PathVariable int stbId, @PathVariable String now) {
        String url = "http://localhost:8080/genre/" + stbId + "/" + now;

        List<TopGenreDto> topGenreDtos = restTemplate.getForObject(url, List.class);
        if(topGenreDtos.size() < 2) {
            
        }

        return service.getEpsdIdList(stbId, now);
    }

    @GetMapping("/flask")
    public List<String> flask() {
        String flaskurl = "http://localhost:5000/1AEB2712-F382-11DE-A716-D9ED132CB42A";
        List<String> result = restTemplate.getForObject(flaskurl, List.class);
        return result;
    }
    
}
