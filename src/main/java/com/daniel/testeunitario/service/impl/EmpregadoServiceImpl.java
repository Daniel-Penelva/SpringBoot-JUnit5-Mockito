package com.daniel.testeunitario.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.daniel.testeunitario.exception.ResourceNotFoundException;
import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.repository.EmpregadoRepository;
import com.daniel.testeunitario.service.EmpregadoService;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class EmpregadoServiceImpl implements EmpregadoService{

    private EmpregadoRepository empregadoRepository;

    @Override
    public Empregado salvarEmpregado(Empregado empregado) {
        Optional<Empregado> criarEmpregado = empregadoRepository.findByEmail(empregado.getEmail());
        
        if(criarEmpregado.isPresent()){
            throw new ResourceNotFoundException("Já existe empregado com esse email: " + empregado.getEmail());
        }
       return empregadoRepository.save(empregado);
    }

    @Override
    public List<Empregado> getAllEmpregados() {
        return empregadoRepository.findAll();
    }

    @Override
    public Optional<Empregado> getEmpregadoById(long id) {
        return empregadoRepository.findById(id);
    }

    @Override
    public Empregado updateEmpregado(Empregado atualizarEmpregado) {
       return empregadoRepository.save(atualizarEmpregado);
    }

    @Override
    public void deleteEmpregado(long id) {
        empregadoRepository.deleteById(id);
    }
    
}
