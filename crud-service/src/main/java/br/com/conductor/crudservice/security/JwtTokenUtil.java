package br.com.conductor.crudservice.security;

import br.com.conductor.crudservice.config.ApplicationProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_CREATED = "created";
    private static final String CLAIM_USERNAME = "sub";
    private static final String CLAIM_ROLES = "roles";

    @Autowired
    private ApplicationProperty applicationProperty;

    public String getUsername(String token) {
        String username;
        try {
            Claims claims = getClaims(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Integer getId(String token) {
        Integer id;
        try {
            id = Integer.parseInt(getClaims(token).get(CLAIM_ID).toString());
        } catch (RuntimeException e) {
            id = null;
        }
        return id;
    }

    public List<GrantedAuthority> getRoles(String token) {
        List<?> roles = (List<?>) getClaims(token).get(CLAIM_ROLES);
        return roles.stream().map(g -> new SimpleGrantedAuthority(g.toString())).collect(Collectors.toList());
    }

    public Date getExpirationDate(String token) {
        Date expiration;
        try {
            Claims claims = getClaims(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaims(token);
            claims.put(CLAIM_CREATED, new Date());
            refreshedToken = construirToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public boolean validate(String token) {
        return !checkExpirationDate(token);
    }

    public String getToken(UsuarioAuth usuarioAuth) {
        return construirToken(construirClaims(usuarioAuth));
    }

    public String getTokenSemExpiracao(UsuarioAuth usuarioAuth) {
        return construirTokenSemExpiracao(construirClaims(usuarioAuth));
    }

    private String construirToken(Map<String, Object> claims) {
        Date date = new Date(System.currentTimeMillis() + getJwtTokenConfig().getExpiracao() * 1000);
        return Jwts.builder().setClaims(claims).setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, getJwtTokenConfig().getSegredo()).compact();
    }

    private String construirTokenSemExpiracao(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, getJwtTokenConfig().getSegredo())
                .compact();
    }

    private Map<String, Object> construirClaims(UsuarioAuth usuarioAuth) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ID, usuarioAuth.getId());
        claims.put(CLAIM_USERNAME, usuarioAuth.getUsername());
        claims.put(CLAIM_ROLES,
                usuarioAuth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        claims.put(CLAIM_CREATED, new Date());
        return claims;
    }

    private Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(getJwtTokenConfig().getSegredo()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private boolean checkExpirationDate(String token) {
        Date dataExpiracao = getExpirationDate(token);
        if (dataExpiracao == null) {
            return false;
        }
        return dataExpiracao.before(new Date());
    }

    private ApplicationProperty.JwtTokenConfig getJwtTokenConfig() {
        return applicationProperty.getSeguranca().getJwtTokenConfig();
    }

}