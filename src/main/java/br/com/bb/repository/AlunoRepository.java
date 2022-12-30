package br.com.bb.repository;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;

import br.com.bb.model.Aluno;
import br.com.bb.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer> {
    public List<Aluno> getTutoradosByProfessor(Professor professor) {

        Objects.requireNonNull(professor, "Professor must be not null");

        var query = find("tutor", Sort.ascending("name"), professor);

        return query.list();
    }
}
