package com.gmarket.api.domain.raffle;

import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoard;
import com.gmarket.api.domain.board.presentgoodsboard.PresentGoodsBoardRepository;
import com.gmarket.api.domain.user.User;
import com.gmarket.api.domain.user.UserRepository;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;

@SpringBootTest
class RaffleTest {

    @Autowired RaffleRepository raffleRepository;

    @Autowired UserRepository userRepository;

    @Autowired PresentGoodsBoardRepository presentGoodsBoardRepository;

    private Long insertedBoardDataId = 1L;
    private Long insertedUserDataId = 1L;
    private Long insertedRaffleId;

    Map<String, Object> createRaffleForm() {

        Map<String, Object> raffleForm = new HashMap<>();

        raffleForm.put("presentBoardId", insertedBoardDataId);
        raffleForm.put("userId", insertedUserDataId);

        return raffleForm;
    }

    @BeforeEach
    void insertData(TestInfo testInfo) {

        // FIX : User Repository 수정본 머지 된 후 변경점 체크하고 수정해야 함.
        // Raffle 생성하기 전에 user 및 나눔 글 생성하는 코드임.
//        Map<String, Object> boardForm = new HashMap<>();
//        boardForm.put("goodsCategory", "DIGITAL");
//        boardForm.put("goodsStatus", "NEW");
//        boardForm.put("raffleCloseAtYear", 2021);
//        boardForm.put("raffleCloseAtMonth", 3);
//        boardForm.put("raffleCloseAtDate", 30);
//        boardForm.put("raffleCloseAtHour", 15);
//        boardForm.put("raffleCloseAtMinute", 30);
//        boardForm.put("viewCount", 0);
//        boardForm.put("status", "CREATE");
//        boardForm.put("title", "test title");
//        boardForm.put("description", "test, test description");
//        boardForm.put("userId", 1);
//
//        Response boardRes = RestAssured.given()
//                .contentType("application/json")
//                .body(boardForm)
//                .when()
//                .post("/boards/share/posts")
//                .then()
//                .extract().response();
//
//        JsonPath boardPath = boardRes.jsonPath();
//        insertedBoardDataId = ((Number)boardPath.get("data.presentBoardId")).longValue();
//
//        Map<String, Object> userForm = new HashMap<>();
//        userForm.put("status", "MANAGER");
//        userForm.put("nickname", "testNick");
//        userForm.put("activityPoint", 0);
//
//        Response userRes = RestAssured.given()
//                .contentType("application/json")
//                .body(userForm)
//                .when()
//                .post("/boards/share/posts")
//                .then()
//                .extract().response();
//
//        JsonPath userPath = userRes.jsonPath();
//        insertedUserDataId = ((Number)userPath.get("data.userId")).longValue();
//
//        System.out.println(insertedBoardDataId);
//        System.out.println(insertedUserDataId);

        if(!testInfo.getTags().contains("SkipCreate")) {

            Response res = RestAssured.given()
                    .contentType("application/json")
                    .body(createRaffleForm())
                    .when()
                    .post("/raffles")
                    .then()
                    .extract().response();

            JsonPath jsonPath = res.jsonPath();
            insertedRaffleId = ((Number)jsonPath.get("data.id")).longValue();
        }
    }

//    @AfterEach
//    void deleteData() {
//
//        presentGoodsBoardRepository.deleteById(insertedBoardDataId);
//
//        userRepository.deleteById(insertedUserDataId);
//
//        raffleRepository.deleteById(insertedRaffleId);
//    }

    @Test
    @Tag("SkipCreate")
    @DisplayName("래플 생성 테스트")
    void raffleCreateTest() {

        Response res = RestAssured.given()
                .contentType("application/json")
                .body(createRaffleForm())
                .when()
                .post("/raffles")
                .then()
                .statusCode(200)
                .assertThat().body("data.presentBoardId", equalTo((int)(long)insertedBoardDataId))
                .assertThat().body("data.userId", equalTo((int)(long)insertedUserDataId))
                .log().all()
                .extract().response();

        JsonPath jsonPath = res.jsonPath();
        insertedRaffleId = ((Number)jsonPath.get("data.id")).longValue();

    }

    @Test
    @DisplayName("래플 조회 테스트")
    void rafflefindTest() {

        RestAssured.given()
                .pathParam("id", insertedBoardDataId)
                .when()
                .get("/raffles/{id}")
                .then()
                .statusCode(200)
                .assertThat().body("data", instanceOf(List.class))
                .log().all();
    }

    @Test
    @DisplayName("래플 삭제 테스트")
    void raffleDeleteTest() {

        RestAssured.given()
                .queryParam("postid", insertedBoardDataId.toString())
                .queryParam("userid", insertedUserDataId.toString())
                .when()
                .delete("/raffles")
                .then()
                .statusCode(204)
                .log().all();

        // FIX : 임시. 수정 완료 후 제거 예정
        Raffle findResult = raffleRepository.findById(insertedRaffleId).orElse(null);
        findResult.reInsert();
        raffleRepository.save(findResult);
    }
}
