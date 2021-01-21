package br.com.conductor.autenticacaoservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        String token = req.getHeader(JwtTokenUtil.AUTH_HEADER);
        if (token != null && token.startsWith(JwtTokenUtil.BEARER_PREFIX)) {
            token = token.substring(7);
        }

        String username = tokenUtil.getUsername(token);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null
                && tokenUtil.validate(token)) {

            UsuarioAuth user = new UsuarioAuth(username, "", tokenUtil.getRoles(token), tokenUtil.getId(token), token);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null,
                    user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(req, res);
    }

}