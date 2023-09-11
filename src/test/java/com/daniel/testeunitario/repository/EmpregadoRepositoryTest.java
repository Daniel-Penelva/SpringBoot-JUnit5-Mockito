package com.daniel.testeunitario.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

    // Criar um empregado
    private Empregado criarEmpregado(){
       
        return Empregado.builder()
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();
    }


    @DisplayName("Teste para criar um empregado")
    @Test
    void testCriarEmpregado() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        // when - criando a condição (o comportamento) a ser testado
        Empregado empregadoCriado = empregadoRepository.save(empregado1);

        // then - verificar mensagem de validação
      assertNotNull(empregadoCriado);
      assertTrue("O ID do empregado criado deve ser maior que 0", empregadoCriado.getId() > 0);
    }

    @DisplayName("Teste para listar empregados")
    @Test
    void testListarTodosEmpregados(){

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        Empregado empregado2 = Empregado.builder()
                .nome("João")
                .sobrenome("da Silva")
                .email("joao@gmail.com").build();

        empregadoRepository.save(empregado1);
        empregadoRepository.save(empregado2);

         // when - criando a condição (o comportamento) a ser testado
        List<Empregado> listaEmpregados = empregadoRepository.findAll();

         // then - verificar mensagem de validação
        assertNotNull(listaEmpregados);
        assertEquals(2, listaEmpregados.size());
    }

    @DisplayName("Teste para buscar empregado por id")
    @Test
    void testBuscarEmpregadoPorId(){

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        empregadoRepository.save(empregado1);
        
         // when - criando a condição (o comportamento) a ser testado
        Empregado buscarEmpregadoId = empregadoRepository.findById(empregado1.getId()).get();

         // then - verificar mensagem de validação
        assertNotNull(buscarEmpregadoId);
    }

    @DisplayName("Teste para atualizar empregado por id")
    @Test
    void testAtualizarEmpregadoPorId(){

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        empregadoRepository.save(empregado1);
        
         // when - criando a condição (o comportamento) a ser testado
        Empregado buscarEmpregadoId = empregadoRepository.findById(empregado1.getId()).get();
        buscarEmpregadoId.setNome("Daniel Up");
        buscarEmpregadoId.setEmail("d4n.penelva@gmail.com");
        buscarEmpregadoId.setSobrenome("Andrade");
        
        Empregado atualizarEmpregado = empregadoRepository.save(buscarEmpregadoId);

         // then - verificar mensagem de validação
        assertNotNull(buscarEmpregadoId);
        assertEquals("Testa se o email foi atualizado", "d4n.penelva@gmail.com", buscarEmpregadoId.getEmail());
        assertEquals("Testa se o nome foi atualizado", "Daniel Up", buscarEmpregadoId.getNome());
    }

    @DisplayName("Teste para deletar empregado por id")
    @Test
    void testDeletarEmpregadoPorId(){

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();
        
        empregadoRepository.save(empregado1);
        
        // when - criando a condição (o comportamento) a ser testado
        empregadoRepository.deleteById(empregado1.getId());
         
         // then - verificar mensagem de validação
         assertTrue(empregadoRepository.findById(empregado1.getId()).isEmpty());
    }
}

