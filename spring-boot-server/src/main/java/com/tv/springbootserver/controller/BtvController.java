package com.tv.springbootserver.controller;

import com.tv.springbootserver.dto.LogDto;
import com.tv.springbootserver.dto.TopGenreDto;
import com.tv.springbootserver.service.BtvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    /* 같은 요일 같은 시간대에 가장 많이 재생한 장르 top 2 구하기 */
    @GetMapping("/genre/{stbId}/{now}")
    public List<TopGenreDto> getTopGenre(@PathVariable int stbId, @PathVariable String now) {
        return service.getTopGenre(stbId, now);
    }

    /* 같은 요일 같은 시간대에 가장 많이 재생한 contentId 구하기 */
    @GetMapping("/content/{stbId}/{now}")
    public String getContentId(@PathVariable int stbId, @PathVariable String now) {
        String contentId = service.getContentId(stbId, now);
        contentId = contentId.substring(1, contentId.length()-1);
        return contentId;
    }

    /* contentId에 해당하는 epsdId들 구하기 */
    @GetMapping("/epsdids/{contentId}")
    public List<String> getEpsdIdsFromContentId(@PathVariable String contentId) {
        return service.getEpsdIdsFromContentId(contentId);
    }

    /* vod 추천 목록 epsdId 가져오기 */
    @GetMapping("/epsd/{stbId}/{now}")
    public List<String> getEpsdIdList(@PathVariable int stbId, @PathVariable String now) {
        List<TopGenreDto> topGenreDtos = getTopGenre(stbId, now);

        /* 데이터가 부족하여 세부장르가 구해지지 않는 경우 */
        if(topGenreDtos.size() < 2) {
            // 같은 요일 같은 시간대에 가장 많이 재생한 contentId를 구하기
            String contentId = getContentId(stbId, now);

            // flas-server에서 협업필터링 알고리즘으로 해당 content와 연관된 content들을 구한다
            String flaskUrl = "http://localhost:5000/" + contentId;
            List<String> contentIds = restTemplate.getForObject(flaskUrl, List.class);

            // contentId들에 해당하는 epsdId들의 list 구하기
            List<String> epsdIdList = new ArrayList<String>();
            for(int i=0; i<contentIds.size(); i++) {
                List<String> epsdIds = getEpsdIdsFromContentId(contentIds.get(i));
                for(int j=0; j<epsdIds.size(); j++) {
                    epsdIdList.add(epsdIds.get(j));
                }
                //System.out.println(contentIds.get(i));
            }
            return epsdIdList;
        }

        return service.getEpsdIdList(stbId, now);
    }


    
}
