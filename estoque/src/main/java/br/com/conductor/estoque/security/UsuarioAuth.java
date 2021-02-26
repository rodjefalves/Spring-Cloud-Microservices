package br.com.conductor.estoque.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UsuarioAuth extends User {

    private static final long serialVersionUID = 1L;

    private String token;
    private Integer id;

    public UsuarioAuth(String username, String password, Collection<? extends GrantedAuthority> permissoes, Integer id,
                       String token) {
        super(username, password, permissoes);
        this.token = token;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return getUsername();
    }

}