package br.com.conductor.estoque.rest.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class AtualizaEstoqueDTO {

    public Integer id;
    public Integer qnt;

}
