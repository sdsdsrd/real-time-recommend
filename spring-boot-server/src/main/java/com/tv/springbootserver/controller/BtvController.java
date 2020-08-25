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

    /* 같은 요일 같은 시간대에 가장 많이 재생한 장르 top 2 구하기 */
    @GetMapping("/genre/{stbId}/{now}")
    public List<TopGenreDto> getTopGenre(@PathVariable int stbId, @PathVariable String now) {
        return service.getTopGenre(stbId, now);
    }

    /* 같은 요일 같은 시간대에 가장 많이 재생한 epsdId 구하기 */
    @GetMapping("/topepsd/{stbId}/{now}")
    public String getTopEpsdId(@PathVariable int stbId, @PathVariable String now) {
        String topEpsdId = service.getTopEpsdId(stbId, now);
        return topEpsdId;
    }

    /* vod 추천 목록 epsdId 가져오기 */
    @GetMapping("/epsd/{stbId}/{now}")
    public List<String> getEpsdIdList(@PathVariable int stbId, @PathVariable String now) {
        List<TopGenreDto> topGenreDtos = getTopGenre(stbId, now);

        boolean hasTwoOrMoreTopGenre = true;
        /* 데이터가 부족하여 세부장르가 구해지지 않는 경우 */
        if(topGenreDtos.size() < 2) {
            hasTwoOrMoreTopGenre = false;
        }
        else {
            String[] mainCategory = {"영화", "드라마", "예능", "시사교양", "다큐", "키즈", "애니", "스포츠", "교육"};
            int mainCtgrCount = 0;
            for(int i=0; i<topGenreDtos.size(); i++) {
                for(int j=0; j<mainCategory.length; j++) {
                    if(topGenreDtos.get(i).getGenre().equals(mainCategory[j])) {
                        mainCtgrCount++;
                        break;
                    }
                }
            }
            if(mainCtgrCount >= 2) {
                hasTwoOrMoreTopGenre = false;
            }
        }

        if(hasTwoOrMoreTopGenre == false) {
            // 같은 요일 같은 시간대에 가장 많이 재생한 epsdId를 구하기
            String epsdId = getTopEpsdId(stbId, now);

            // flask-server에서 협업필터링 알고리즘으로 해당 content와 연관된 content들을 구한다
            String flaskUrl = "http://54.180.30.116:5000/" + epsdId;
            List<String> epsdIds = restTemplate.getForObject(flaskUrl, List.class);

            return epsdIds;
        }

        return service.getEpsdIdList(stbId, now);
    }


    
}
