package ridi.web.configurations;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ridi.modelos.persistence.UsuarioRidi;
import ridi.web.services.UsuarioRidiService;
import ridi.web.services.authorization.JwtService;
import ridi.web.util.auth.Credential;
import ridi.web.util.errors.Errors;
import ridi.web.util.jackson.RidiMapper;
import ridi.web.util.response.RidiResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    final JwtService jwtService;
    final UsuarioRidiService usuarioRidiService;
    final RidiMapper ridiMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpRequest,
                                    @NonNull HttpServletResponse httpResonse,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filter(httpRequest);
            filterChain.doFilter(httpRequest, httpResonse);
        } catch (UnsupportedJwtException | MalformedJwtException |
                 SignatureException | IllegalArgumentException e) {
            logger.error(e);
            response(httpResonse, RidiResponse.error()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Token provided is unrecognized")
                    .error(Errors.INVALID_TOKEN_ERROR)
                    .build());
        } catch (ExpiredJwtException e) {
            logger.error(e);
            response(httpResonse, RidiResponse.error()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("Token expired")
                    .error(Errors.TOKEN_EXPIRED_ERROR)
                    .build());
        } catch (UsernameNotFoundException e) {
            logger.error(e);
            response(httpResonse, RidiResponse.error()
                    .status(HttpStatus.UNAUTHORIZED)
                    .message("User in token is unrecognized")
                    .error(Errors.USER_NOTFOUND_ERROR)
                    .build());
        }
    }

    private void filter(@NonNull HttpServletRequest httpRequest) {
        final String token = getToken(httpRequest);
        final UUID id;

        if (token == null) return;
        if (jwtService.isExpired(token)) return;
        id = UUID.fromString(jwtService.parse(token).getSubject());

        if (SecurityContextHolder.getContext().getAuthentication() != null) return;

        UsuarioRidi user = usuarioRidiService.findById(id);
        logger.info("User is entering: " + user);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user,
                Credential.by(user),
                user.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private String getToken(@NonNull HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void response(@NonNull HttpServletResponse httpResponse, @NonNull RidiResponse message) {
        try {
            httpResponse.setStatus(message.getStatusCode().value());
            httpResponse.setContentType("application/json");

            try (OutputStream stream = httpResponse.getOutputStream()) {
                ridiMapper.writeValue(stream, message.getBody());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
