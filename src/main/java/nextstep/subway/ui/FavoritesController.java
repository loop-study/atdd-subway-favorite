package nextstep.subway.ui;

import nextstep.auth.authorization.AuthenticationPrincipal;
import nextstep.subway.applicaion.FavoriteService;
import nextstep.subway.applicaion.dto.FavoriteRequest;
import nextstep.subway.applicaion.dto.FavoriteResponse;
import nextstep.member.domain.LoginMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoritesController {
    private final FavoriteService favoriteService;

    public FavoritesController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping()
    public ResponseEntity<Void> createFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                               @RequestBody FavoriteRequest request) {
        FavoriteResponse response = favoriteService.createFavorite(request);
        return ResponseEntity.created(URI.create("/favorites/" + response.getId())).build();
    }

    @GetMapping()
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@AuthenticationPrincipal LoginMember loginMember) {
        List<FavoriteResponse> response = favoriteService.findAll();
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<List<FavoriteResponse>> removeFavorite(@AuthenticationPrincipal LoginMember loginMember,
                                                                 @PathVariable Long favoriteId) {
        favoriteService.removeFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}

