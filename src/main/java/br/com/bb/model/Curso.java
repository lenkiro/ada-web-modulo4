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



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CURSOS")
public class Curso{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "curso_id")
    private Integer id;

    @Column(name = "curso_name", nullable = false)
    private String name;

    @Column(name = "curso_description", nullable = false)
    private String description;
    
    @Column(name = "curso_duration", nullable = false)
    private int duration;

    
}
