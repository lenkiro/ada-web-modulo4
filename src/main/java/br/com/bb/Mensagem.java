package br.com.bb;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mensagem {
    public String boasvindas(String nome){
        return "hi " + nome;
    }
}
