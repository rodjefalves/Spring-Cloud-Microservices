package br.com.conductor.autenticacaoservice.rest.dto;

import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class UsuarioDTO {

    /* Modelo JSON:
     {
       "login": "login",
       "senha": "senha"
     }
     */

    private String login;

    private String senha;

}