package com.tv.springbootserver.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BtvControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("로그 가져오기")
    @Test
    void checkGetLog() throws Exception {
        mockMvc.perform(get("/log"))
            .andExpect(status().isOk());
    }

    @DisplayName("같은 요일 같은 시간대에 가장 많이 재생한 장르 top 2 구하기 - 입력값 정상")
    @Test
    void checkGetTopGenre() throws Exception {
        mockMvc.perform(get("/genre/{stbId}/{now}", "1", "20200815093000.237"))
                .andExpect(status().isOk());
    }

    @DisplayName("같은 요일 같은 시간대에 가장 많이 재생한 epsdId 구하기")
    @Test
    void checkGetTopEpsdId() throws Exception {
        mockMvc.perform(get("/topepsd/{stbId}/{now}", "1", "20200828123000.237"))
                .andExpect(status().isOk());
    }

    @DisplayName("vod 추천 목록 epsdId 가져오기 - 경우1")
    @Test
    void checkGetEpsdIdList1() throws Exception {
        mockMvc.perform(get("/epsd/{stbId}/{now}", "1", "20200815093000.237"))
                .andExpect(jsonPath("$.*", Matchers.hasSize(8)));
    }

    @DisplayName("vod 추천 목록 epsdId 가져오기 - 경우2")
    @Test
    void checkGetEpsdIdList2() throws Exception {
        mockMvc.perform(get("/epsd/{stbId}/{now}", "1", "20200824123000.237"))
                .andExpect(jsonPath("$.*", Matchers.hasSize(8)));
    }

    @DisplayName("vod 추천 목록 epsdId 가져오기 - 경우3")
    @Test
    void checkGetEpsdIdList3() throws Exception {
        mockMvc.perform(get("/epsd/{stbId}/{now}", "1", "20200828183000.237"))
                .andExpect(status().isOk());
    }
}