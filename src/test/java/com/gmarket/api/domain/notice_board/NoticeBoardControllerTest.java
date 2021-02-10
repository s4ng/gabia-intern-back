package com.gmarket.api.domain.notice_board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest
class NoticeBoardControllerTest {

    @Autowired
    private MockRestServiceServer server;

    @Test
    void 공지사항_생성() {
    }

    @Test
    void 공지사항_목록() {
    }

    @Test
    void 글_조회() {
        server.expect(requestTo("/boards/notice/posts"))
                .andRespond(withSuccess(new ClassPathResource("/notice-mock.json", getClass()),
                        MediaType.APPLICATION_JSON));
    }

    @Test
    void 글_수정() {
    }

    @Test
    void 글_삭제() {
    }
}