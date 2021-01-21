package br.com.conductor.crudservice.rest.dto;

import br.com.conductor.crudservice.entity.Produto;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class ProdutoDTO {

    private Integer id;

    private String nome;

    private Integer estoque;

    private BigDecimal valor;
}
