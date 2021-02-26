package br.com.conductor.crudservice.config;

import br.com.conductor.crudservice.entity.Produto;
import br.com.conductor.crudservice.rest.dto.ProdutoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /*@Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ProdutoDTO.class, Produto.class).addMapping(scr -> src.);

        return new ModelMapper();
    }*/
}
