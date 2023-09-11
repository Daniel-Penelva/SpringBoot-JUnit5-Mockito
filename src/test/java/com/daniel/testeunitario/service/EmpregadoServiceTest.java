package com.daniel.testeunitario.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
//A dicionado essa importação
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.repository.EmpregadoRepository;
import com.daniel.testeunitario.service.impl.EmpregadoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmpregadoServiceTest {

    @Mock
    private EmpregadoRepository EmpregadoRepository;

    @InjectMocks
    private EmpregadoServiceImpl empregadoServiceImpl;

    // Criar um empregado
    private Empregado criarEmpregado() {

        return Empregado.builder()
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();
    }


    @DisplayName("Teste para criar um empregado")
    @Test
    void testCriarEmpregado() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado = criarEmpregado();
        
        given(EmpregadoRepository.findByEmail(empregado.getEmail())).willReturn(Optional.empty());
        given(EmpregadoRepository.save(empregado)).willReturn(empregado);

        // when - criando a condição (o comportamento) a ser testado
        Empregado criarEmpregado = empregadoServiceImpl.salvarEmpregado(empregado);

        // then - verificar mensagem de validação
        assertTrue(criarEmpregado != null);
    }
}
