package com.daniel.testeunitario.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.daniel.testeunitario.model.Empregado;

@DataJpaTest
public class EmpregadoRepositoryTest {

    /*
     * Teste unitário apenas na camada Data JPA
     *
     * given - dado a condição antes da configuração.
     * when - ação sobre o comportamento que vamos testar.
     * then - verificar a solidez
     */

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @DisplayName("Teste para criar um empregado")
    @Test
    void testCriarEmpregado() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = Empregado.builder()
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();

        // when - criando a condição (o comportamento) a ser testado
        Empregado empregadoCriado = empregadoRepository.save(empregado1);

        // then - verificar mensagem de validação
      assertNotNull(empregadoCriado);
      assertTrue("O ID do empregado criado deve ser maior que 0", empregadoCriado.getId() > 0);
    }
}
