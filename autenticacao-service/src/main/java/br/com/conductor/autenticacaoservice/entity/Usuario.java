package br.com.conductor.autenticacaoservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_sequence")
    private Integer id;

    @Column
    private String login;

    @Column
    private String senha;

    @Column
    private boolean admin;

}