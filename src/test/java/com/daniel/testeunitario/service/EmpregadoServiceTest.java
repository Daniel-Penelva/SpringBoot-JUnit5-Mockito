package com.daniel.testeunitario.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
//Adicionado essa importação
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.daniel.testeunitario.exception.ResourceNotFoundException;
import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.repository.EmpregadoRepository;
import com.daniel.testeunitario.service.impl.EmpregadoServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class EmpregadoServiceTest {

    @Mock
    private EmpregadoRepository empregadoRepository;

    @InjectMocks
    private EmpregadoServiceImpl empregadoServiceImpl;

    // Criar um empregado
    private Empregado criarEmpregado() {

        return Empregado.builder()
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();
    }


    /**
     * Explicando cada linha de código - testCriarEmpregado():
     * 
     * 1. `@DisplayName("Teste para criar um empregado")`: Esta é uma anotação do JUnit 5 que define o nome do teste que será exibido na saída 
     *     do teste. É usado para dar um nome descritivo ao teste.
     * 
     * 2. `@Test`: Esta é uma anotação do JUnit 5 que indica que o método é um método de teste.
     * 
     * 3. `void testCriarEmpregado()`: Este é o método de teste em si. Ele verifica o comportamento de criar um empregado quando não há conflito 
     *     de email (ou seja, o email do empregado não existe no repositório).
     *
     * 4. `Empregado empregado = criarEmpregado();`: Aqui, cria um objeto `Empregado` usando o método `criarEmpregado()`. Este será o objeto 
     *     que tentará salvar.
     * 
     * 5. `given(empregadoRepository.findByEmail(empregado.getEmail())).willReturn(Optional.empty());`: Usando o Mockito, configura um 
     *     comportamento para o mock `empregadoRepository`. Aqui, diz que quando o método `findByEmail` for chamado com o email do empregado que 
     *     está tentando criar, ele deve retornar um `Optional` vazio. Isso simula a situação em que não há empregado com o mesmo email no 
     *     repositório.
     * 
     * 6. `given(empregadoRepository.save(empregado)).willReturn(empregado);`: Aqui, configura outro comportamento para o mock 
     *    `empregadoRepository`. Aqui, diz que quando o método `save` for chamado com o empregado, ele deve retornar o próprio empregado. Isso 
     *    é comum ao salvar um objeto, onde geralmente o objeto salvo é retornado.
     * 
     * 7. `Empregado criarEmpregado = empregadoServiceImpl.salvarEmpregado(empregado);`: Aqui, chama o método `salvarEmpregado` do serviço 
     *    `empregadoServiceImpl` com o empregado que você configurou nos passos anteriores.
     * 
     * 8. `assertTrue(criarEmpregado != null);`: Aqui, verifica se o objeto retornado pelo método `salvarEmpregado` não é nulo. Em outras 
     *     palavras, está verificando se a operação de salvar o empregado foi bem-sucedida e retornou um objeto empregado.
     * 
     * Em resumo, esse teste unitário verifica se o método `salvarEmpregado` funciona corretamente quando não há conflito de email, ou seja, 
     * quando o email do empregado não existe no repositório. Ele também garante que o método retorne um objeto empregado não nulo após a 
     * operação de salvamento.
    */
    
    @DisplayName("Teste para criar um empregado")
    @Test
    void testCriarEmpregado() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado = criarEmpregado();

        given(empregadoRepository.findByEmail(empregado.getEmail())).willReturn(Optional.empty());
        given(empregadoRepository.save(empregado)).willReturn(empregado);

        // when - criando a condição (o comportamento) a ser testado
        Empregado criarEmpregado = empregadoServiceImpl.salvarEmpregado(empregado);

        // then - verificar mensagem de validação
        assertTrue(criarEmpregado != null);
    }

    /**
     * Explicando cada linha de código - testCriarEmpregadoComThrowException():
     * 
     * 1. `@DisplayName("Teste para criar um empregado com throw Exception")`: Esta é uma anotação do JUnit 5 que define o nome do teste que 
     *     será exibido na saída do teste. É usado para dar um nome descritivo ao teste.
     * 
     * 2. `@Test`: Esta é uma anotação do JUnit 5 que indica que o método é um método de teste.
     * 
     * 3. `void testCriarEmpregadoComThrowException()`: Este é o método de teste em si. Ele verifica o comportamento de criar um empregado quando 
     *     uma exceção é lançada.
     * 
     * 4. `Empregado empregado = criarEmpregado();`: Aqui, cria um objeto `Empregado` usando o método `criarEmpregado()`. Este será o objeto 
     *     que tentará salvar.
     * 
     * 5. `given(empregadoRepository.findByEmail(empregado.getEmail())).willReturn(Optional.of(empregado));`: Usando o Mockito, configura um 
     *     comportamento para o mock `empregadoRepository`. Aqui, diz que quando o método `findByEmail` for chamado com o email do empregado que 
     *     está tentando criar, ele deve retornar um `Optional` contendo esse empregado. Isso simula a situação em que um empregado com o mesmo 
     *     email já existe no repositório.
     * 
     * 6. `assertThrows(ResourceNotFoundException.class, () -> { empregadoServiceImpl.salvarEmpregado(empregado); });`: Aqui, está usando o 
     *     método `assertThrows` do JUnit 5 para verificar se uma exceção do tipo `ResourceNotFoundException` é lançada quando você chama 
     *     `empregadoServiceImpl.salvarEmpregado(empregado);`. Isso é feito usando uma expressão lambda.
     * 
     * 7. `Mockito.verify(empregadoRepository, never()).save(any(Empregado.class));`: Por fim, usa o Mockito para verificar se o método `save` 
     *     do repositório nunca foi chamado (já que esperamos que ele não seja chamado devido à exceção lançada).
     * 
     * Em resumo, esse teste verifica se o método `salvarEmpregado` lança a exceção correta quando um empregado com o mesmo email já existe no 
     * repositório e se o método `save` não é chamado nessa situação. Isso garante que o comportamento de lançamento de exceção esteja correto 
     * quando necessário.
    */
    @DisplayName("Teste para criar um empregado com throw Exception")
    @Test
    void testCriarEmpregadoComThrowException() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado = criarEmpregado();
        
        given(empregadoRepository.findByEmail(empregado.getEmail())).willReturn(Optional.of(empregado));

        // when - criando a condição (o comportamento) a ser testado
       assertThrows(ResourceNotFoundException.class, () -> {
        empregadoServiceImpl.salvarEmpregado(empregado);
       });

        // then - verificar mensagem de validação
        Mockito.verify(empregadoRepository, never()).save(any(Empregado.class));
    }
}
