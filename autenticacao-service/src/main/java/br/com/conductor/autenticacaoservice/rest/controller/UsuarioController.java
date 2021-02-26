package br.com.conductor.autenticacaoservice.rest.controller;


import br.com.conductor.autenticacaoservice.entity.Usuario;
import br.com.conductor.autenticacaoservice.rest.dto.CredenciaisDTO;
import br.com.conductor.autenticacaoservice.rest.dto.TokenDTO;
import br.com.conductor.autenticacaoservice.security.JwtTokenUtil;
import br.com.conductor.autenticacaoservice.service.JwtService;
import br.com.conductor.autenticacaoservice.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@Api(value = "Autenticação")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final JwtTokenUtil tokenUtil;

    @Autowired
    private final UsuarioServiceImpl usuarioService;

    @Autowired
    private final PasswordEncoder encoder;

    @ApiOperation(value = "Criar usuário")
    @PostMapping("/api/usuarios")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario dto) {
        String senhaCriptografada = encoder.encode(dto.getSenha());
        dto.setSenha(senhaCriptografada);
        return usuarioService.salvar(dto);
    }

    @ApiOperation(value = "Autenticar usuário")
    @PostMapping("/api/usuarios/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
