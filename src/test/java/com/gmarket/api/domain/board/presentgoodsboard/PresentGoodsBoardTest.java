package com.gmarket.api.domain.board.presentgoodsboard;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@SpringBootTest
@Transactional
class PresentGoodsBoardTest {
//
//    @Autowired PresentGoodsBoardRepository presentGoodsBoardRepository;
//
//    private Long insertedDataId;
//
//    Map<String, Object> createRequestForm() {
//
//        Map<String, Object> requestForm = new HashMap<>();
//        requestForm.put("goodsCategory", "DIGITAL");
//        requestForm.put("goodsStatus", "NEW");
//        requestForm.put("raffleCloseAtYear", 2021);
//        requestForm.put("raffleCloseAtMonth", 3);
//        requestForm.put("raffleCloseAtDate", 30);
//        requestForm.put("raffleCloseAtHour", 15);
//        requestForm.put("raffleCloseAtMinute", 30);
//        requestForm.put("viewCount", 0);
//        requestForm.put("status", "CREATE");
//        requestForm.put("title", "test title");
//        requestForm.put("description", "test, test description");
//        requestForm.put("userId", 1);
//
//        return requestForm;
//    }
//
//    @BeforeEach
//    void insertData(TestInfo testInfo) {
//        if(testInfo.getTags().contains("SkipCleanup")) {
//            return;
//        }
//
//        Response res = RestAssured.given()
//                .contentType("application/json")
//                .body(createRequestForm())
//                .when()
//                .post("/boards/share/posts")
//                .then()
//                .extract().response();
//
//        JsonPath jsonPath = res.jsonPath();
//        insertedDataId = ((Number)jsonPath.get("data.boardId")).longValue();
//    }
//
//
//    @AfterEach
//    void deleteData(TestInfo testInfo) {
//        if(testInfo.getTags().contains("SkipCleanup")) {
//            return;
//        }
//
//        if(insertedDataId != null) {
//            presentGoodsBoardRepository.deleteById(insertedDataId);
//        }
//    }
//
//    @Test
//    @Tag("SkipCleanup")
//    void 나눔글_생성() {
//
//        // 생성 테스트
//        Map<String, Object> requestForm = createRequestForm();
//
//        Response res = RestAssured.given()
//                .contentType("application/json")
//                .body(requestForm).log().all()
//                .when()
//                .post("/boards/share/posts")
//                .then()
//                .statusCode(200)
//                .assertThat().body("data.title", equalTo("test title"))
//                .assertThat().body("data.userId", equalTo(1))
//                .log().all()
//                .extract().response();
//
//        JsonPath jsonPath = res.jsonPath();
//        insertedDataId = ((Number)jsonPath.get("data.boardId")).longValue();
//    }
//
//    @Test
//    void 나눔글_조회() {
//        // id 확인 테스트
//        RestAssured.given()
//                .pathParam("id", insertedDataId)
//                .when()
//                .get("/boards/share/posts/{id}")
//                .then()
//                .statusCode(200)
//                .assertThat().body("data.boardId", equalTo((int)(long)insertedDataId))
//                .log().all();
//    }
//
//    @Test
//    void 나눔글_수정() {
//        // 수정 테스트
//        Map<String, Object> map = createRequestForm();
//        map.put("title", "change title");
//
//        RestAssured.given()
//                .contentType("application/json")
//                .pathParam("id", insertedDataId)
//                .body(map).log().all()
//                .when()
//                .put("/boards/share/posts/{id}")
//                .then()
//                .statusCode(200)
//                .assertThat().body("data.title", equalTo("change title"))
//                .log().all();
//    }
//
//    @Test
//    void 나눔글_삭제() {
//        // 삭제 테스트
//        RestAssured.given()
//                .pathParam("id", insertedDataId)
//                .when()
//                .delete("/boards/share/posts/{id}")
//                .then()
//                .statusCode(204)
//                .log().all();
//    }
//
//    @Test
//    void 나눔글_페이지_확인() {
//
//        RestAssured.given()
//                .queryParam("page", "1")
//                .when()
//                .get("/boards/share/posts")
//                .then()
//                .statusCode(200)
//                .assertThat().body("data", instanceOf(List.class))
//                .log().all();
//    }

}