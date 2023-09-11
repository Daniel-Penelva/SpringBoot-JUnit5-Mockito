package com.daniel.testeunitario.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
//Adicionado essa importação
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

import java.util.Collections;
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


    /**
     * Explicando cada linha de código - testListarEmpregados():
     * 
     * 1. `@DisplayName("Teste para criar listar empregados")`: Esta é uma anotação do JUnit 5 que define o nome do teste que será exibido na saída 
     *     do teste.
     * 
     * 2. `@Test`: Esta é uma anotação do JUnit 5 que indica que o método é um método de teste.
     * 
     * 3. `Empregado empregado1 = criarEmpregado();`: Você cria um objeto `Empregado` chamado `empregado1` usando o método `criarEmpregado()`.
     * 
     * 4. `Empregado empregado2 = Empregado.builder()...`: Aqui, você cria manualmente um segundo objeto `Empregado` chamado `empregado2`. Este 
     *     será outro empregado que você adicionará à lista simulada de empregados retornada pelo repositório.
     * 
     * 5. `given(empregadoRepository.findAll()).willReturn(List.of(empregado1, empregado2));`: Você usa o Mockito para configurar o 
     *    comportamento do mock `empregadoRepository`. Você diz que quando o método `findAll` for chamado, ele deve retornar uma lista contendo 
     *    `empregado1` e `empregado2`. Isso simula a situação em que o repositório retorna uma lista de empregados quando o método `getAllEmpregados` é chamado.
     * 
     * 6.  `List<Empregado> listaEmpregados = empregadoServiceImpl.getAllEmpregados();`: Aqui, você chama o método `getAllEmpregados` do serviço, 
     *      que internamente deve chamar o método `findAll` do repositório (que você configurou para retornar os empregados `empregado1` e 
     *      `empregado2`).
     * 
     * 7. `assertNotNull(listaEmpregados);`: Você verifica se a lista de empregados retornada não é nula, garantindo que o serviço não retorne 
     *    uma lista nula.
     * 
     * 8. `assertEquals(2, listaEmpregados.size());`: Você verifica se o tamanho da lista retornada é igual a 2, o que verifica se o serviço 
     *     está retornando corretamente os dois empregados simulados.
     * 
     * Em resumo, este teste verifica se o método `getAllEmpregados` do serviço `empregadoServiceImpl` retorna uma lista de empregados com o 
     * tamanho correto e que a lista não é nula. Isso é feito usando o Mockito para simular o comportamento do repositório ao chamar `findAll`.
    */
    @DisplayName("Teste listar todos os empregados")
    @Test
    void testListarEmpregados() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        Empregado empregado2 = Empregado.builder()
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();
        
        given(empregadoRepository.findAll()).willReturn(List.of(empregado1, empregado2));

        // when - criando a condição (o comportamento) a ser testado
       List<Empregado> listaEmpregados = empregadoServiceImpl.getAllEmpregados();

        // then - verificar mensagem de validação
        assertNotNull(listaEmpregados);
        assertEquals(2, listaEmpregados.size());
    }


/**
 * Explicando cada linha de código - testListarCollecaoVaziaEmpregados()
 * 
 * 1. `@DisplayName("Teste para retornar uma lista vazia")`: Esta é uma anotação do JUnit 5 que define o nome do teste que será exibido na saída 
 *     do teste.
 * 
 * 2. `@Test`: Esta é uma anotação do JUnit 5 que indica que o método é um método de teste.
 * 
 * 3. `Empregado empregado1 = criarEmpregado();`: Aqui, cria um objeto `Empregado` chamado `empregado1` usando o método `criarEmpregado()`. Este 
 *     será um empregado simulado que não usará neste teste.
 * 
 * 4. `Empregado empregado2 = Empregado.builder()...`: Aqui, cria manualmente um segundo objeto `Empregado` chamado `empregado2`. Este será 
 *     outro empregado simulado que não usará neste teste.
 * 
 * 5. `given(empregadoRepository.findAll()).willReturn(Collections.emptyList());`: Aqui, usa o Mockito para configurar o comportamento do mock 
 *    `empregadoRepository`. VAqui, diz que quando o método `findAll` for chamado, ele deve retornar uma lista vazia (representada por 
 *    `Collections.emptyList()`). Isso simula a situação em que o repositório não possui empregados e retorna uma lista vazia quando o método 
 *    `getAllEmpregados` é chamado.
 * 
 * 6. `List<Empregado> listaEmpregados = empregadoServiceImpl.getAllEmpregados();`: Aqui, chama o método `getAllEmpregados` do serviço, 
 *     que internamente deve chamar o método `findAll` do repositório (que você configurou para retornar uma lista vazia).
 * 
 * 7. `assertTrue(listaEmpregados.isEmpty());`: Aqui, verifica se a lista de empregados retornada está vazia usando `assertTrue`. Isso garante 
 *     que o serviço retorne uma lista vazia quando não há empregados no repositório.
 * 
 * 8. `assertEquals(0, listaEmpregados.size());`: Aqui, verifica se o tamanho da lista retornada é igual a 0. Isso é outra maneira de garantir 
 *     que a lista está vazia.
 * 
 * Em resumo, este teste verifica se o método `getAllEmpregados` do serviço `empregadoServiceImpl` retorna uma lista vazia quando o repositório 
 * não possui empregados. Isso é feito configurando o comportamento do repositório com o Mockito para retornar uma lista vazia quando o método 
 * `findAll` é chamado.
*/
    @DisplayName("Teste para retornar uma lista vazia")
    @Test
    void testListarCollecaoVaziaEmpregados() {

        // Given - gerando os dados antes do condicionamento.
        Empregado empregado1 = criarEmpregado();

        Empregado empregado2 = Empregado.builder()
                .nome("João")
                .sobrenome("Silva")
                .email("joao@gmail.com").build();
        
        given(empregadoRepository.findAll()).willReturn(Collections.emptyList());

        // when - criando a condição (o comportamento) a ser testado
       List<Empregado> listaEmpregados = empregadoServiceImpl.getAllEmpregados();

        // then - verificar mensagem de validação
        assertTrue(listaEmpregados.isEmpty());
        assertEquals(0, listaEmpregados.size());
    }
}
