package nextstep.subway.unit;

import nextstep.member.domain.Favorite;
import nextstep.member.domain.FavoriteRepository;
import nextstep.subway.applicaion.dto.SectionRequest;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.Station;
import nextstep.subway.domain.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("즐겨찾기 관리 - 실객체")
@SpringBootTest
@Transactional
class FavoriteServiceTest {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @DisplayName("즐겨찾기를 생성한다")
    @Test
    void craeteFavorite() {
        // given
        Station 강남역 = stationRepository.save(new Station("강남역"));
        Station 역삼역 = stationRepository.save(new Station("역삼역"));

        // when
        Favorite favorite = favoriteRepository.save(new Favorite(강남역, 역삼역));

        // then
        assertThat(favorite).isNotNull();
    }

    @DisplayName("즐겨찾기를 조회")
    @Test
    void getFavorites() {
        // given
        Station 강남역 = stationRepository.save(new Station("강남역"));
        Station 역삼역 = stationRepository.save(new Station("역삼역"));

        favoriteRepository.save(new Favorite(강남역, 역삼역));

        // when
        List<Favorite> list = favoriteRepository.findAll();

        // then
        assertThat(list).hasSize(1);
    }

    @DisplayName("즐겨찾기를 제거")
    @Test
    void deleteFavorite() {
        // given
        Station 강남역 = stationRepository.save(new Station("강남역"));
        Station 역삼역 = stationRepository.save(new Station("역삼역"));

        Favorite favorite = favoriteRepository.save(new Favorite(강남역, 역삼역));

        // when
        favoriteRepository.deleteById(favorite.getId());

        // then
        List<Favorite> list = favoriteRepository.findAll();
        assertThat(list).hasSize(0);
    }
}
