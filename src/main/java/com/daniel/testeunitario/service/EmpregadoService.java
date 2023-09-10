package com.daniel.testeunitario.service;

import java.util.List;
import java.util.Optional;

import com.daniel.testeunitario.model.Empregado;

public interface EmpregadoService {

    Empregado salvarEmpregado(Empregado empregado);

    List<Empregado> getAllEmpregados();

    Optional<Empregado> getEmpregadoById(long id);

    Empregado updateEmpregado(Empregado atualizarEmpregado);

    void deleteEmpregado(long id);
}
