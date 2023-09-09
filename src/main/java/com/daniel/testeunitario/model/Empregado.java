package com.daniel.testeunitario.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "empregados")
public class Empregado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;
    
    @Column(name = "sobrenome", nullable = false)
    private String sobrenome;
    
    @Column(name = "email", nullable = false)
    private String email;
}

/**
 * Observação:
 * 
 * A classe Empregado é anotada com @Builder. Isso permite criar uma instância da classe Empregado usando um padrão fluente, onde pode definir 
 * os valores dos campos usando métodos encadeados. 
*/