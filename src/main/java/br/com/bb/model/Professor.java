package br.com.bb.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PROFESSORES")
public class Professor{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "professor_id",unique = true,nullable = false)
    private Integer id;

    @Column(name = "professor_name", nullable = false)
    private String name;

    
}

//Samuel native query things
// @NamedNativeQueries({
//     @NamedNativeQuery(name = "CONSULTAR_ALUNOS", query = "SELECT * FROM ALUNO", resultClass = Aluno.class),
//     @NamedNativeQuery(name = "CONSULTAR_ALUNO_POR_MATRICULA", query = "SELECT * FROM ALUNO WHERE matricula = :matricula", resultClass = Aluno.class)
// })
