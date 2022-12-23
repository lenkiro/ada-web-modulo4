package br.com.bb.repository;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class ProfessorRepository implements PanacheRepositoryBase<Professor,Integer>{
    
}
