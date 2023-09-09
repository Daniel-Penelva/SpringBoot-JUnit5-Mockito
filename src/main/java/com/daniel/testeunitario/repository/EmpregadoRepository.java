package com.daniel.testeunitario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.testeunitario.model.Empregado;

public interface EmpregadoRepository extends JpaRepository<Empregado, Long>{

    Optional<Empregado> findByEmail(String email);
    
}
