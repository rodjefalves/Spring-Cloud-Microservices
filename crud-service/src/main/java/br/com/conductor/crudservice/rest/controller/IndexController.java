package br.com.conductor.crudservice.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class IndexController {

    @GetMapping("/loja")
    public ModelAndView homePage(){
        ModelAndView mv = new ModelAndView("/index2");
        return mv;
    }

    @GetMapping("/loja2")
    public String homePage2(){
        return "Tudo certo!!";
    }
}
