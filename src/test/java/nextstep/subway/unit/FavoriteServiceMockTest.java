package nextstep.subway.unit;

import nextstep.subway.applicaion.FavoriteService;
import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.subway.domain.Favorite;
import nextstep.subway.domain.FavoriteRepository;
import nextstep.subway.applicaion.StationService;
import nextstep.subway.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("즐겨찾기 관리 - Mock객체")
@ExtendWith(MockitoExtension.class)
class FavoriteServiceMockTest {
    @Mock
    private StationService stationService;

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    private Station 강남역;
    private Station 역삼역;

    private FavoriteRequest request;
    private Favorite favorite;

    @BeforeEach
    void setUp() {
        강남역 = new Station(1L, "강남역");
        역삼역 = new Station(2L, "역삼역");

        request = createRequest(강남역, 역삼역);
        favorite = craeteFavoriteEntity(강남역, 역삼역);
    }

    @DisplayName("즐겨찾기를 생성한다")
    @Test
    void craeteFavorite() {
        // given
        when(stationService.findById(any())).thenReturn(강남역)
                                            .thenReturn(역삼역);
        when(favoriteRepository.save(any())).thenReturn(favorite);

        // when
        FavoriteResponse response = favoriteService.createFavorite(request);

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("즐겨찾기를 조회")
    @Test
    void getFavorites() {
        // given
        when(stationService.findById(any())).thenReturn(강남역)
                                            .thenReturn(역삼역);
        when(favoriteRepository.save(any())).thenReturn(favorite);
        when(favoriteRepository.findAll()).thenReturn(Arrays.asList(favorite));

        favoriteService.createFavorite(request);

        // when
        List<FavoriteResponse> list = favoriteService.findAll();

        // then
        assertThat(list).hasSize(1);
    }

    @DisplayName("즐겨찾기를 제거")
    @Test
    void deleteFavorite() {
        // given
        when(stationService.findById(any())).thenReturn(강남역)
                .thenReturn(역삼역);
        when(favoriteRepository.save(any())).thenReturn(favorite);
        when(favoriteRepository.findAll()).thenReturn(new ArrayList<>());

        favoriteService.createFavorite(request);

        // when
        favoriteService.removeFavorite(favorite.getId());

        // then
        List<Favorite> list = favoriteRepository.findAll();
        assertThat(list).hasSize(0);
    }

    private Favorite craeteFavoriteEntity(Station source, Station target) {
        return new Favorite(source, target);
    }

    private FavoriteRequest createRequest(Station source, Station target) {
        return new FavoriteRequest(source.getId(), target.getId());
    }
}
