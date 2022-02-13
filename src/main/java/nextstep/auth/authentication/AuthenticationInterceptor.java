package nextstep.auth.authentication;

import nextstep.auth.context.Authentication;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthenticationInterceptor implements HandlerInterceptor {
    private AuthenticationConverter converter;

    public AuthenticationInterceptor(AuthenticationConverter authenticationConverter) {
        this.converter = authenticationConverter;
    }

    public abstract void afterAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException;
}
