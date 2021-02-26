package br.com.conductor.pagamentoservice.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizarEstoqueDTO {

    public Integer id;
    public Integer qnt;
}
