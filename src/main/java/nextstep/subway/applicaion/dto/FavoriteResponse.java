package nextstep.subway.applicaion.dto;

import nextstep.subway.domain.Favorite;

public class FavoriteResponse {
    private Long id;
    private StationResponse source;
    private StationResponse target;

    public FavoriteResponse(Long id, StationResponse source, StationResponse target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public static FavoriteResponse of(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                StationResponse.of(favorite.getUpStation()),
                StationResponse.of(favorite.getDownStation())
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StationResponse getSource() {
        return source;
    }

    public void setSource(StationResponse source) {
        this.source = source;
    }

    public StationResponse getTarget() {
        return target;
    }

    public void setTarget(StationResponse target) {
        this.target = target;
    }
}
