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
                .id(1L)
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
    

    /**
     * Explicando cada linha de código - testBuscarEmpregadPorId()
     * 
     * 1. `@DisplayName("Teste para buscar empregado por id")`: Esta anotação é usada para definir um nome descritivo para o teste que será 
     *     exibido quando o teste for executado. É uma boa prática dar nomes descritivos aos testes para que você possa entender facilmente o 
     *     que o teste está fazendo.
     * 
     * 2. `@Test`: Esta anotação indica que o método é um teste que deve ser executado pelo mecanismo de teste JUnit.
     * 
     * 3. `void testBuscarEmpregadPorId()`: Este é o método de teste em si. Ele é responsável por testar se a busca de um empregado por ID 
     *     funciona corretamente.
     * 
     * 4. `// Given - gerando os dados antes do condicionamento.`: Nesta seção está preparando o cenário do teste. Cria um objeto `Empregado` 
     *     usando o método `criarEmpregado()`.
     * 
     * 5. `given(empregadoRepository.findById(1L)).willReturn(Optional.of(empregado));`: Esta linha está configurando um comportamento simulado 
     *     usando o Mockito. Ela diz que, quando o método `findById` do `empregadoRepository` for chamado com o argumento `1L`, ele deve retornar 
     *     um `Optional` contendo o objeto `empregado` que foi criado anteriormente.
     * 
     * 6. `// when - criando a condição (o comportamento) a ser testado`: Nesta seção, executa a ação que deseja testar. Aqui, chama o método 
     *    `getEmpregadoById` do `empregadoServiceImpl` com o ID do empregado que foi configurado no cenário (neste caso, `1L`).
     * 
     * 7. `Empregado buscarEmpregado = empregadoServiceImpl.getEmpregadoById(empregado.getId()).get();`: Aqui, está armazenando o resultado da 
     *     busca em uma variável chamada `buscarEmpregado`.
     * 
     * 8. `// then - verificar mensagem de validação`: Nesta seção, Aqui, está fazendo as verificações para garantir que o comportamento seja o 
     *     esperado.
     *        - `assertNotNull(buscarEmpregado);`: VAqui, verifica se o objeto `buscarEmpregado` não é nulo. Isso garante que a busca retornou 
     *           algum resultado.
     *        - `assertEquals("Comparando se pertence ao mesmo id", empregado, buscarEmpregado);`: Aqui, compara se o objeto `buscarEmpregado` é 
     *           igual ao objeto `empregado` que foi criado no cenário. Isso verifica se os detalhes do empregado recuperado correspondem ao que 
     *           você configurou no cenário.
     * 
     * No geral, este teste garante que a busca de empregados por ID está funcionando corretamente, retornando o empregado correto e não nulo. 
     * Ele também faz uma comparação para garantir que o empregado recuperado seja igual ao empregado configurado no cenário, verificando se 
     * eles têm os mesmos detalhes.
    */
    @DisplayName("Teste para buscar empregado por id")
    @Test
    void testBuscarEmpregadPorId(){
        
        // Given - gerando os dados antes do condicionamento.
        Empregado empregado = criarEmpregado();
        given(empregadoRepository.findById(1L)).willReturn(Optional.of(empregado));

        // when - criando a condição (o comportamento) a ser testado 
       Empregado buscarEmpregado = empregadoServiceImpl.getEmpregadoById(empregado.getId()).get();
    
        // then - verificar mensagem de validação
        assertNotNull(buscarEmpregado);
        assertEquals("Comparando se pertence ao mesmo id", empregado, buscarEmpregado);
    }


    /**
     * Explicando cada linha de código - testAtualizarEmpregadPorId()
     * 
     * 1. `void testAtualizarEmpregadPorId()`: Este é o método de teste em si. Ele é responsável por testar se a atualização de um empregado 
     *     funciona corretamente.
     * 
     * 2. `// Given - gerando os dados antes do condicionamento.`: Nesta seção, está preparando o cenário do teste. Aqui, cria um objeto 
     *     `Empregado` usando o método `criarEmpregado()`. Em seguida, configura o comportamento simulado do método `save` do 
     *     `empregadoRepository` usando o Mockito. Isso significa que, quando o método `save` for chamado com o objeto `empregado`, ele deve 
     *      retornar o mesmo objeto `empregado`. Depois, atualiza os detalhes do `empregado`, mudando o nome para "Daniel Up" e o email para 
     *     "d4n.penelva@gmail.com".
     * 
     * 3. `// when - criando a condição (o comportamento) a ser testado`: Nesta seção, executa a ação que deseja testar. Aqui, chama o método 
     *    `updateEmpregado` do `empregadoServiceImpl` com o objeto `empregado` que foi configurado no cenário.
     * 
     * 4. `Empregado atualizarEmpregado = empregadoServiceImpl.updateEmpregado(empregado);`: Aqui, está armazenando o resultado da atualização 
     *     em uma variável chamada `atualizarEmpregado`.
     * 
     * 5. `// then - verificar mensagem de validação`: Nesta seção, está fazendo as verificações para garantir que o comportamento seja o 
     *    esperado.
     *       - `assertNotNull(atualizarEmpregado);`: Aqui, verifica se o objeto `atualizarEmpregado` não é nulo. Isso garante que a atualização 
     *          tenha retornado algum resultado.
     *       - `assertEquals(atualizarEmpregado.getEmail(), empregado.getEmail());`: Aqui, verifica se o email do objeto `atualizarEmpregado` é 
     *          igual ao email que você configurou no cenário. Isso garante que a atualização ocorreu com sucesso.
     *       - `assertEquals("Daniel Up", empregado.getNome());`: Aqui, verifica se o nome do objeto `empregado` foi atualizado corretamente 
     *          para "Daniel Up".
     * 
     * No geral, este teste garante que a atualização de empregados funcione corretamente, atualizando os detalhes do empregado de acordo com as 
     * mudanças feitas antes de verificar se a atualização foi bem-sucedida.
     * */
    @DisplayName("Teste para atualizar empregado")
    @Test
    void testAtualizarEmpregadPorId(){
        
        // Given - gerando os dados antes do condicionamento.
        Empregado empregado = criarEmpregado();
        given(empregadoRepository.save(empregado)).willReturn(empregado);
        empregado.setNome("Daniel Up");
        empregado.setEmail("d4n.penelva@gmail.com");

        // when - criando a condição (o comportamento) a ser testado 
       Empregado atualizarEmpregado = empregadoServiceImpl.updateEmpregado(empregado);
    
        // then - verificar mensagem de validação
        assertNotNull(atualizarEmpregado);
        assertEquals(atualizarEmpregado.getEmail(), empregado.getEmail()); //ou
        assertEquals("Daniel Up", empregado.getNome());
    }
}
