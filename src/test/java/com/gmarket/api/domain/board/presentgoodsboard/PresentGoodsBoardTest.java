package com.gmarket.api.domain.board.presentgoodsboard;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

@SpringBootTest
@Transactional
class PresentGoodsBoardTest {

    @Autowired PresentGoodsBoardRepository presentGoodsBoardRepository;

    Map<String, Object> createRequestForm() {

        Map<String, Object> requestForm = new HashMap<>();
        requestForm.put("goodsCategory", "DIGITAL");
        requestForm.put("goodsStatus", "NEW");
        requestForm.put("raffleCloseAtYear", 2021);
        requestForm.put("raffleCloseAtMonth", 3);
        requestForm.put("raffleCloseAtDate", 30);
        requestForm.put("raffleCloseAtHour", 15);
        requestForm.put("raffleCloseAtMinute", 30);
        requestForm.put("viewCount", 0);
        requestForm.put("status", "CREATE");
        requestForm.put("title", "test title");
        requestForm.put("description", "test, test description");
        requestForm.put("userId", 1);

        return requestForm;
    }

    @Test
    @Order(1)
    void 나눔글_생성_확인_수정_삭제() {

        // 생성 테스트
        Map<String, Object> requestForm = createRequestForm();

        Response res = RestAssured.given()
                    .contentType("application/json")
                    .body(requestForm).log().all()
                .when()
                    .post("/boards/share/posts")
                .then()
                    .statusCode(200)
                    .assertThat().body("data.title", equalTo("test title"))
                    .assertThat().body("data.userId", equalTo(1))
                    .log().all()
                    .extract().response();

        JsonPath jsonPath = res.jsonPath();
        int boardId = jsonPath.get("data.boardId");


        // id 확인 테스트
        RestAssured.given()
                .pathParam("id", boardId)
                .when()
                .get("/boards/share/posts/{id}")
                .then()
                .statusCode(200)
                .assertThat().body("data.boardId", equalTo(boardId))
                .log().all();


        // 수정 테스트
        Map<String, Object> map = createRequestForm();
        map.put("title", "change title");

        RestAssured.given()
                .contentType("application/json")
                .pathParam("id", boardId)
                .body(map).log().all()
                .when()
                .put("/boards/share/posts/{id}")
                .then()
                .statusCode(200)
                .assertThat().body("data.title", equalTo("change title"))
                .log().all();


        // 삭제 테스트
        RestAssured.given()
                .pathParam("id", boardId)
                .when()
                .delete("/boards/share/posts/{id}")
                .then()
                .statusCode(204)
                .log().all();


        // DB 삭제
        presentGoodsBoardRepository.deleteById((long) boardId);
    }


    @Test
    @Order(2)
    void 나눔글_페이지_확인() {

        RestAssured.given()
                        .queryParam("page", "1")
                    .when()
                        .get("/boards/share/posts")
                    .then()
                        .statusCode(200)
                        .assertThat().body("data", instanceOf(List.class))
                        .log().all();
    }

}