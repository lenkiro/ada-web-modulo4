package br.com.bb.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.model.Curso;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CursoRepository implements PanacheRepositoryBase<Curso,Integer>{
    
}