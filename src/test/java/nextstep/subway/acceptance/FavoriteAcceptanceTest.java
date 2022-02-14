package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static nextstep.subway.acceptance.FavoriteSteps.*;
import static nextstep.subway.acceptance.LineSteps.지하철_노선_생성_요청;
import static nextstep.subway.acceptance.MemberSteps.*;
import static nextstep.subway.acceptance.StationSteps.지하철역_생성_요청;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("즐겨찾기 관리 기능")
class FavoriteAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    private String accessToken;

    private Long 강남역;
    private Long 삼성역;

    private Long 이호선;

    /**
     * Background
     * Given 지하철역 등록되어 있음
     * And 지하철 노선 등록되어 있음
     * And 지하철 노선에 지하철역 등록되어 있음
     * And 회원 등록되어 있음
     * And 로그인 되어있음
     */
    @BeforeEach
    public void setUp() {
        super.setUp();

        강남역 = 지하철역_생성_요청("강남역").jsonPath().getLong("id");
        삼성역 = 지하철역_생성_요청("삼성역").jsonPath().getLong("id");

        Map<String, String> lineCreateParams = createLineCreateParams(강남역, 삼성역);
        이호선 = 지하철_노선_생성_요청(lineCreateParams).jsonPath().getLong("id");

        회원_생성_요청(EMAIL, PASSWORD, AGE);
        accessToken = 로그인_되어_있음(EMAIL, PASSWORD);
    }

    /**
     * When 즐겨찾기 생성을 요청
     * Then 즐겨찾기 생성됨
     */
    @DisplayName("즐겨찾기 생성을 요청")
    @Test
    void createFavorite() {
        // When
        ExtractableResponse<Response> response = 즐겨찾기_생성_요청(accessToken, 강남역, 삼성역);

        // Then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * When 즐겨찾기 목록 조회 요청
     * Then 즐겨찾기 목록 조회됨
     */
    @DisplayName("즐겨찾기 목록 조회를 요청.")
    @Test
    void getFavorites() {

    }
    /**
     * When 즐겨찾기 삭제 요청
     * Then 즐겨찾기 삭제됨
     */
    @DisplayName("즐겨찾기 생성을 요청.")
    @Test
    void deleteFavorite() {

    }

    private Map<String, String> createLineCreateParams(Long upStationId, Long downStationId) {
        Map<String, String> lineCreateParams;
        lineCreateParams = new HashMap<>();
        lineCreateParams.put("name", "신분당선");
        lineCreateParams.put("color", "bg-red-600");
        lineCreateParams.put("upStationId", upStationId + "");
        lineCreateParams.put("downStationId", downStationId + "");
        lineCreateParams.put("distance", 10 + "");
        return lineCreateParams;
    }
}