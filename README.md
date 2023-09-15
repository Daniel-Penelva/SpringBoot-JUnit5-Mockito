# Documentação API Spring Boot com JUnit5 e Mockito

Este documento fornecerá uma visão geral abrangente da API Spring, incluindo sua estrutura, funcionalidades principais e como foram testados suas funcionalidades usando as bibliotecas JUnit e Mockito.

Alguns dos destaques incluem:

- **Spring Boot:** Foi utilizado Spring Boot para simplificar o desenvolvimento, a configuração e a implantação do microserviço.

- **API RESTful:** Foi implementado na API seguindo os princípios de REST, tornando-a fácil de consumir e escalonar.

- **JUnit5 e Mockito:** Para garantir a qualidade do código e a funcionalidade do microserviço foi utilizado as bibliotecas JUnit e Mockito para testes automatizados. Isso permite criar testes unitários e de integração eficazes.

## Classe Empregado

A classe `Empregado` é uma entidade JPA (Java Persistence API) que representa um empregado em no sistema. Está classe é usada para mapear os dados do empregado para uma tabela no banco de dados.

```java
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
```

### Estrutura da Classe

A classe `Empregado` possui os seguintes atributos:

- `id` (Tipo: Long): Identificador exclusivo do empregado.
- `nome` (Tipo: String, Não Nulo): O primeiro nome do empregado.
- `sobrenome` (Tipo: String, Não Nulo): O sobrenome do empregado.
- `email` (Tipo: String, Não Nulo): O endereço de e-mail do empregado.

### Anotações

A classe `Empregado` faz uso de várias anotações JPA para mapear corretamente a entidade para o banco de dados:

- `@Entity`: Indica que esta classe é uma entidade JPA.
- `@Table(name = "empregados")`: Especifica o nome da tabela no banco de dados onde os registros desta entidade serão armazenados.
- `@Id`: Indica que o campo `id` é a chave primária da entidade.
- `@GeneratedValue(strategy = GenerationType.IDENTITY)`: Define a estratégia de geração de valor automático para o campo `id`.
- `@Column`: Define as colunas da tabela e suas propriedades, como nome e obrigatoriedade.

### Utilização do Lombok

Além das anotações JPA, esta classe faz uso do projeto Lombok para gerar automaticamente métodos como getters, setters, construtores e muito mais. Isso torna o código mais conciso e legível.

A anotação `@Builder` do Lombok permite criar instâncias da classe `Empregado` usando um padrão fluente, onde os valores dos campos podem ser definidos usando métodos encadeados.

```java
Empregado empregado = Empregado.builder()
    .nome("João")
    .sobrenome("Silva")
    .email("joao.silva@example.com")
    .build();
```

## Interface EmpregadoRepository

A interface `EmpregadoRepository` é uma parte importante do projeto, responsável pela comunicação com o banco de dados para operações relacionadas aos empregados. Está interface estende a `JpaRepository` do Spring Data JPA, fornecendo métodos prontos para a realização de operações CRUD (Create, Read, Update, Delete) na entidade `Empregado`.

```java
package com.daniel.testeunitario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniel.testeunitario.model.Empregado;

public interface EmpregadoRepository extends JpaRepository<Empregado, Long>{

    Optional<Empregado> findByEmail(String email);
    
}
```

### Métodos Personalizados

Além dos métodos herdados da `JpaRepository`, esta interface define um método personalizado:

#### `Optional<Empregado> findByEmail(String email)`

Este método permite buscar um empregado com base no seu endereço de e-mail. Ele retorna um `Optional`, o que significa que o empregado pode ou não ser encontrado. Isso é útil quando não temos certeza se o empregado com o e-mail fornecido existe no banco de dados.

### Herança da JpaRepository

A `EmpregadoRepository` herda todos os métodos da `JpaRepository`, incluindo:

- `save()`: Salva um empregado no banco de dados.
- `findById()`: Encontra um empregado pelo seu ID.
- `findAll()`: Retorna todos os empregados no banco de dados.
- `deleteById()`: Exclui um empregado pelo seu ID.
- Outros métodos úteis para consultas e atualizações.

Essa interface facilita muito a interação com o banco de dados e torna as operações relacionadas aos empregados simples e diretas.

## Interface EmpregadoService

A interface `EmpregadoService` define um contrato para operações relacionadas aos empregados no sistema. Ela serve como um ponto de entrada para manipular dados de empregados, fornecendo métodos para criar, recuperar, atualizar e excluir empregados.

```java
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
```

### Métodos da Interface

A `EmpregadoService` inclui os seguintes métodos:

#### `Empregado salvarEmpregado(Empregado empregado)`

Este método permite salvar um novo empregado no sistema. Ele recebe um objeto `Empregado` como parâmetro e retorna o empregado salvo.

#### `List<Empregado> getAllEmpregados()`

Este método retorna uma lista de todos os empregados cadastrados no sistema. É útil para recuperar todos os registros de empregados.

#### `Optional<Empregado> getEmpregadoById(long id)`

Este método permite recuperar um empregado pelo seu ID. Ele recebe o ID do empregado como parâmetro e retorna um `Optional` que pode conter o empregado correspondente, se encontrado.

#### `Empregado updateEmpregado(Empregado atualizarEmpregado)`

Este método é usado para atualizar os dados de um empregado existente. Ele recebe um objeto `Empregado` com as informações atualizadas e retorna o empregado atualizado.

#### `void deleteEmpregado(long id)`

Este método permite excluir um empregado com base no seu ID. Ele recebe o ID do empregado a ser excluído como parâmetro e não retorna nenhum valor.

### Uso da Interface

A `EmpregadoService` é uma parte fundamental de nossa aplicação, pois fornece uma camada de abstração entre os controladores da API e a camada de persistência de dados. Ao utilizar esta interface, podemos manter o código organizado e desacoplado, facilitando a manutenção e o teste de nossos serviços.

## Classe EmpregadoServiceImpl

A classe `EmpregadoServiceImpl` é a implementação concreta da interface `EmpregadoService` no sistema. Está classe é responsável por fornecer a lógica de negócios para manipular empregados, incluindo operações de criação, leitura, atualização e exclusão (CRUD).

```java
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
```

### Métodos da Classe

A `EmpregadoServiceImpl` implementa os seguintes métodos definidos na interface `EmpregadoService`:

#### `Empregado salvarEmpregado(Empregado empregado)`

Este método permite salvar um novo empregado no sistema. Ele verifica se já existe um empregado com o mesmo endereço de e-mail e, se já existir, lança uma exceção `ResourceNotFoundException`. Caso contrário, salva o novo empregado no banco de dados e o retorna.

#### `List<Empregado> getAllEmpregados()`

Este método retorna uma lista de todos os empregados cadastrados no sistema. Ele delega a consulta ao `EmpregadoRepository`.

#### `Optional<Empregado> getEmpregadoById(long id)`

Este método permite recuperar um empregado pelo seu ID. Ele delega a consulta ao `EmpregadoRepository` e retorna um `Optional` que pode conter o empregado correspondente, se encontrado.

#### `Empregado updateEmpregado(Empregado atualizarEmpregado)`

Este método é usado para atualizar os dados de um empregado existente no sistema. Ele delega a operação ao `EmpregadoRepository` e retorna o empregado atualizado.

#### `void deleteEmpregado(long id)`

Este método permite excluir um empregado pelo seu ID. Ele delega a operação ao `EmpregadoRepository` e não retorna nenhum valor.

### Exceções

A classe `EmpregadoServiceImpl` lida com exceções usando a classe `ResourceNotFoundException`. Quando uma exceção é lançada, ela sinaliza que um recurso não foi encontrado e pode ser tratada apropriadamente no controlador da API ou em outras partes do código.

```java
package com.daniel.testeunitario.exception;

public class ResourceNotFoundException extends RuntimeException{
    
    public ResourceNotFoundException(String message){
        super(message);
    }
}
```

### Utilização da Classe

A classe `EmpregadoServiceImpl` é injetada em outras partes da aplicação para fornecer a lógica de negócios relacionada aos empregados. Ela desempenha um papel fundamental na manipulação de dados de empregados e garante a integridade e a segurança dos registros.

## Classe EmpregadoController

A classe `EmpregadoController` é responsável por expor os endpoints da API relacionados aos empregados. Essa classe recebe solicitações HTTP, chama os métodos apropriados do serviço `EmpregadoService` e retorna respostas HTTP correspondentes.

```java
package com.daniel.testeunitario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.service.EmpregadoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/empregados")
@AllArgsConstructor
public class EmpregadoController {

    private EmpregadoService empregadoService;

    // Criar empregado - http://localhost:8080/api/empregados
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Empregado criarEmpregado(@RequestBody Empregado empregado) {
        return empregadoService.salvarEmpregado(empregado);
    }

    // Listar todos os empregados - http://localhost:8080/api/empregados
    @GetMapping
    public List<Empregado> findAllEmpregados() {
        return empregadoService.getAllEmpregados();
    }

    
    // Buscar empregado por id - http://localhost:8080/api/empregados/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Empregado> findByIdEmpregado(@PathVariable("id") Long id) {
        return empregadoService.getEmpregadoById(id).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar empregado - http://localhost:8080/api/empregados/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Empregado> atualizarPorIdEmpregado(@PathVariable Long id, @RequestBody Empregado empregado){
        return empregadoService.getEmpregadoById(id).map(salvarEmpregado -> {
            salvarEmpregado.setNome(empregado.getNome());
            salvarEmpregado.setSobrenome(empregado.getSobrenome());
            salvarEmpregado.setEmail(empregado.getEmail());

            Empregado empregadoAtualizado = empregadoService.updateEmpregado(salvarEmpregado);
            return new ResponseEntity<>(empregadoAtualizado, HttpStatus.OK);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar empregado - http://localhost:8080/api/empregados/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarPorIdEmpregado(@PathVariable Long id){
        empregadoService.deleteEmpregado(id);
        return new ResponseEntity<String>("Empregado deletado com Sucesso!", HttpStatus.OK);
    }
}
```

### Endpoints da API

#### Criar Empregado

- **Método:** `POST`
- **URL:** `/api/empregados`
- **Descrição:** Cria um novo empregado no sistema.
- **Corpo da Solicitação:** Um objeto `Empregado` no formato JSON.
- **Resposta HTTP de Sucesso:** Status `201 Created` e o empregado recém-criado.

#### Listar Todos os Empregados

- **Método:** `GET`
- **URL:** `/api/empregados`
- **Descrição:** Retorna uma lista de todos os empregados cadastrados no sistema.
- **Resposta HTTP de Sucesso:** Status `200 OK` e uma lista de empregados no formato JSON.

#### Buscar Empregado por ID

- **Método:** `GET`
- **URL:** `/api/empregados/{id}`
- **Descrição:** Retorna um empregado pelo seu ID.
- **Parâmetro da URL:** `id` - O ID do empregado a ser recuperado.
- **Resposta HTTP de Sucesso:** Status `200 OK` e o empregado no formato JSON.
- **Resposta HTTP de Falha:** Status `404 Not Found` se o empregado não for encontrado.

#### Atualizar Empregado por ID

- **Método:** `PUT`
- **URL:** `/api/empregados/{id}`
- **Descrição:** Atualiza os dados de um empregado existente com base no ID.
- **Parâmetro da URL:** `id` - O ID do empregado a ser atualizado.
- **Corpo da Solicitação:** Um objeto `Empregado` no formato JSON com os novos dados.
- **Resposta HTTP de Sucesso:** Status `200 OK` e o empregado atualizado no formato JSON.
- **Resposta HTTP de Falha:** Status `404 Not Found` se o empregado não for encontrado.

#### Deletar Empregado por ID

- **Método:** `DELETE`
- **URL:** `/api/empregados/{id}`
- **Descrição:** Exclui um empregado pelo seu ID.
- **Parâmetro da URL:** `id` - O ID do empregado a ser excluído.
- **Resposta HTTP de Sucesso:** Status `200 OK` e uma mensagem indicando que o empregado foi deletado com sucesso.

## Uso da Classe

A classe `EmpregadoController` atua como uma camada de controle que recebe solicitações da API e direciona essas solicitações para o serviço `EmpregadoService`. Ela mapeia os endpoints da API para métodos específicos e lida com a lógica de resposta HTTP.

# Testando com JUnit5 e Mockito

Testar a API é fundamental para garantir que ele funcione conforme o esperado. Foi utilizado as bibliotecas JUnit e Mockito para escrever e executar testes automatizados. Alguns dos tipos de testes que realizamos incluem:

- **Testes Unitários:** Foi isolado componentes individuais, como serviços e controladores, para testar seu comportamento em um ambiente controlado.

- **Testes de Integração:** Foi verificado a interação entre vários componentes do microserviço para garantir que eles funcionem bem juntos.

Forneceremos exemplos detalhados de como escrever e executar esses testes em seções subsequentes desta documentação.

Este documento servirá como um guia completo para entender o microserviço de API Spring Boot com JUnit e Mockito. 


## Classe de Teste EmpregadoRepositoryTest

A classe `EmpregadoRepositoryTest` é uma classe de teste unitário que testa a funcionalidade da camada de repositório do sistema. Ela verifica se as operações CRUD (Create, Read, Update, Delete) na entidade `Empregado` estão funcionando corretamente.

```java
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
```

### Anotações JUnit Jupiter

Esta classe utiliza o framework de teste JUnit Jupiter e faz uso de várias anotações para configurar e executar os testes:

- `@DataJpaTest`: Essa anotação é usada para configurar o contexto de teste para testes que envolvem o acesso a dados JPA. Ela configura automaticamente um banco de dados em memória e configura os componentes necessários para testar o acesso a dados.

### Métodos de Teste

A classe `EmpregadoRepositoryTest` contém vários métodos de teste, cada um focado em testar uma funcionalidade específica. Aqui estão os principais métodos de teste:

#### Teste para Criar um Empregado

- **Método:** `testCriarEmpregado`
- **Descrição:** Este teste verifica se é possível criar um novo empregado no sistema e se o ID gerado para o empregado é maior que zero.
- **Resultado Esperado:** O empregado é criado com sucesso e seu ID é maior que zero.

#### Teste para Listar Todos os Empregados

- **Método:** `testListarTodosEmpregados`
- **Descrição:** Este teste verifica se é possível listar todos os empregados cadastrados no sistema.
- **Resultado Esperado:** Uma lista contendo todos os empregados é retornada e possui o tamanho esperado.

#### Teste para Buscar um Empregado por ID

- **Método:** `testBuscarEmpregadoPorId`
- **Descrição:** Este teste verifica se é possível buscar um empregado pelo seu ID.
- **Resultado Esperado:** O empregado é encontrado com sucesso.

#### Teste para Atualizar um Empregado por ID

- **Método:** `testAtualizarEmpregadoPorId`
- **Descrição:** Este teste verifica se é possível atualizar os dados de um empregado existente no sistema.
- **Resultado Esperado:** O empregado é atualizado com sucesso e os dados são consistentes após a atualização.

#### Teste para Deletar um Empregado por ID

- **Método:** `testDeletarEmpregadoPorId`
- **Descrição:** Este teste verifica se é possível deletar um empregado pelo seu ID.
- **Resultado Esperado:** O empregado é deletado com sucesso e não pode ser encontrado após a exclusão.

## Uso da Classe

A classe `EmpregadoRepositoryTest` é essencial para garantir a integridade e o funcionamento correto da camada de repositório do sistema. Ela verifica se as operações de persistência e recuperação de dados estão funcionando conforme o esperado.

## Classe de Teste EmpregadoServiceTest

O script em Java `EmpregadoServiceTest` é uma classe de teste que verifica o comportamento da classe `EmpregadoServiceImpl`. Neste documento, explicaremos cada método de teste presente na classe.

```java
package com.daniel.testeunitario.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
//Adicionado essa importação
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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


    @DisplayName("Teste para deletar empregado por id")
    @Test
    void testDeletarEmpregadPorId(){
        
        // Given - gerando os dados antes do condicionamento.
        long empregadoId = 1L;
        doNothing().when(empregadoRepository).deleteById(empregadoId);

        // when - criando a condição (o comportamento) a ser testado 
       empregadoServiceImpl.deleteEmpregado(empregadoId);
    
        // then - verificar mensagem de validação
        verify(empregadoRepository, times(1)).deleteById(empregadoId);
    }
}
```

### Anotações JUnit Jupiter e Mockito

Esta classe utiliza o framework de teste JUnit Jupiter em combinação com o framework de mock Mockito para configurar e executar os testes. Ela também faz uso de várias anotações para configurar o ambiente de teste.

- **@ExtendWith(MockitoExtension.class)**: Essa anotação estende o suporte do JUnit Jupiter para o Mockito, permitindo o uso de mocks durante os testes.

- **@SpringBootTest**: Esta anotação é usada para indicar que os testes estão sendo executados em um contexto Spring Boot. Isso permite que o teste use as configurações e funcionalidades do Spring Boot.

### Métodos de Teste

A classe `EmpregadoServiceTest` contém vários métodos de teste, cada um focado em testar uma funcionalidade específica do serviço `EmpregadoServiceImpl`. Aqui estão os principais métodos de teste:

#### Teste: `testCriarEmpregado`

Este teste verifica se o método `salvarEmpregado` do serviço funciona corretamente quando não há conflito de email, ou seja, quando o email do empregado não existe no repositório.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - O comportamento simulado do método `findByEmail` do `empregadoRepository` é configurado para retornar um `Optional` vazio.
  - O comportamento simulado do método `save` do `empregadoRepository` é configurado para retornar o próprio empregado.

- **When (Quando)**:
  - O método `salvarEmpregado` do serviço `empregadoServiceImpl` é chamado com o empregado criado.

- **Then (Então)**:
  - Verifica-se se o objeto retornado pelo método `salvarEmpregado` não é nulo.

#### Teste: `testCriarEmpregadoComThrowException`

Este teste verifica se o método `salvarEmpregado` do serviço lança a exceção correta quando um empregado com o mesmo email já existe no repositório.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - O comportamento simulado do método `findByEmail` do `empregadoRepository` é configurado para retornar um `Optional` contendo o empregado.

- **When (Quando)**:
  - O método `salvarEmpregado` do serviço `empregadoServiceImpl` é chamado com o empregado criado.

- **Then (Então)**:
  - Verifica-se se uma exceção do tipo `ResourceNotFoundException` é lançada.
  - Verifica-se se o método `save` do `empregadoRepository` nunca foi chamado.

#### Teste: `testListarEmpregados`

Este teste verifica se o método `getAllEmpregados` do serviço retorna uma lista de empregados com o tamanho correto e que a lista não é nula.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - Um segundo objeto `Empregado` é criado manualmente.
  - O comportamento simulado do método `findAll` do `empregadoRepository` é configurado para retornar uma lista contendo os empregados criados.

- **When (Quando)**:
  - O método `getAllEmpregados` do serviço `empregadoServiceImpl` é chamado.

- **Then (Então)**:
  - Verifica-se se a lista de empregados retornada não é nula.
  - Verifica-se se o tamanho da lista retornada é igual a 2.

#### Teste: `testListarCollecaoVaziaEmpregados`

Este teste verifica se o método `getAllEmpregados` do serviço retorna uma lista vazia quando o repositório não possui empregados.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - Um segundo objeto `Empregado` é criado manualmente.
  - O comportamento simulado do método `findAll` do `empregadoRepository` é configurado para retornar uma lista vazia.

- **When (Quando)**:
  - O método `getAllEmpregados` do serviço `empregadoServiceImpl` é chamado.

- **Then (Então)**:
  - Verifica-se se a lista de empregados retornada está vazia.
  - Verifica-se se o tamanho da lista retornada é igual a 0.

#### Teste: `testBuscarEmpregadPorId`

Este teste verifica se o método `getEmpregadoById` do serviço retorna o empregado correto com base no ID fornecido.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - O comportamento simulado do método `findById` do `empregadoRepository` é configurado para retornar o empregado criado.

- **When (Quando)**:
  - O método `getEmpregadoById` do serviço `empregadoServiceImpl` é chamado com o ID do empregado criado.

- **Then (Então)**:
  - Verifica-se se o objeto retornado não é nulo.
  - Verifica-se se os detalhes do empregado recuperado correspondem aos detalhes do empregado configurado no cenário.

#### Teste: `testAtualizarEmpregadPorId`

Este teste verifica se o método `updateEmpregado` do serviço atualiza corretamente os detalhes do empregado.

- **Given (Dado)**:
  - Um objeto `Empregado` é criado usando o método `criarEmpregado()`.
  - O comportamento simulado do método `save` do `empregadoRepository` é configurado para retornar o próprio empregado.
  - Os detalhes do empregado são atualizados manualmente.

- **When (Quando)**:
  - O método `updateEmpregado` do serviço `empregadoServiceImpl` é chamado com o empregado configurado no cenário.

- **Then (Então)**:
  - Verifica-se se o objeto retornado não é nulo.
  - Verifica-se se o email do empregado foi atualizado corretamente.
  - Verifica-se se o nome do empregado foi atualizado corretamente.

#### Teste: `testDeletarEmpregadPorId`

Este teste verifica se o método `deleteEmpregado` do serviço exclui o empregado correto com base no ID fornecido.

- **Given (Dado)**:
  - Um ID de empregado é definido.
  - O comportamento simulado do método `deleteById` do `empregadoRepository` é configurado para não fazer nada.

- **When (Quando)**:
  - O método `deleteEmpregado` do serviço `empregadoServiceImpl` é chamado com o ID do empregado.

- **Then (Então)**:
  - Verifica-se se o método `deleteById` do `empregadoRepository` foi chamado exatamente uma vez com o ID correto.

### Uso da Classe

A classe `EmpregadoServiceTest` é essencial para garantir a integridade e o funcionamento correto do serviço `EmpregadoServiceImpl`. Ela verifica se as operações de criação, listagem, busca, atualização e exclusão de empregados estão funcionando conforme o esperado, contribuindo para a robustez do sistema.


## Classe de Teste EmpregadoControllerTest

Este documento fornece uma visão geral da classe de teste `EmpregadoControllerTest`, que testa o controlador `EmpregadoController` em um ambiente de teste usando o framework JUnit Jupiter.

```java
package com.daniel.testeunitario.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

//Adicionando essas importações static
import static org.mockito.BDDMockito.given; 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; 
import static org.hamcrest.CoreMatchers.is; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; 

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*; 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.service.impl.EmpregadoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmpregadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpregadoServiceImpl empregadoServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    // Criar um empregado
    private Empregado criarEmpregado() {

        return Empregado.builder()
                .id(1L)
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();
    }

    @DisplayName("Teste para criar um empregado")
    @Test
    void testCriarEmpregado() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

        given(empregadoServiceMock.salvarEmpregado(any(Empregado.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // when - utilizando o import static -> 'import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;'
        ResultActions response = mockMvc.perform(post("/api/empregados")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(empregado)));

        // then
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(empregado.getNome())))
                .andExpect(jsonPath("$.sobrenome", is(empregado.getSobrenome())))
                .andExpect(jsonPath("$.email", is(empregado.getEmail())));
    }


    @DisplayName("Teste para listar empregados")
    @Test
    void testListarEmpregados() throws Exception {

        // given
        List<Empregado> listaEmpregado = new ArrayList<>();
        listaEmpregado.add(Empregado.builder().nome("Jão").sobrenome("Silva").email("jao@gmail.com").build());
        listaEmpregado.add(Empregado.builder().nome("Patricia").sobrenome("Nunes").email("patricia@gmail.com").build());
        listaEmpregado.add(Empregado.builder().nome("Pedro").sobrenome("Marques").email("pedro@gmail.com").build());

        given(empregadoServiceMock.getAllEmpregados()).willReturn(listaEmpregado);

        // when
        ResultActions response = mockMvc.perform(get("/api/empregados"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listaEmpregado.size())));
    }

    @DisplayName("Teste para buscar empregado por id")
    @Test
    void testBuscarEmpregadoPorId() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

        given(empregadoServiceMock.getEmpregadoById(empregado.getId())).willReturn(Optional.of(empregado));

        // when
        ResultActions response = mockMvc.perform(get("/api/empregados/{id}", empregado.getId()));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nome", is(empregado.getNome())))
                .andExpect(jsonPath("$.sobrenome", is(empregado.getSobrenome())))
                .andExpect(jsonPath("$.email", is(empregado.getEmail())));
    }


    @DisplayName("Teste para buscar empregado por id não encontrado")
    @Test
    void testBuscarEmpregadoPorIdNãoEncontrado() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

        given(empregadoServiceMock.getEmpregadoById(empregado.getId())).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/api/empregados/{id}", empregado.getId()));

        //then
        response.andExpect(status().isNotFound())
        .andDo(print());
    }

    @DisplayName("Teste para atualizar empregado por id")
    @Test
    void testAtualizarEmpregadoPorId() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

        Empregado empregadoAtualizado = Empregado.builder()
                .id(1L)
                .nome("Daniel Up")
                .sobrenome("Penelva Up")
                .email("d4n.andrade@gmail.com").build();

        given(empregadoServiceMock.getEmpregadoById(empregado.getId())).willReturn(Optional.of(empregado));

        given(empregadoServiceMock.updateEmpregado(any(Empregado.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/empregados/{id}", empregado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empregadoAtualizado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.nome", is(empregadoAtualizado.getNome())))
                .andExpect(jsonPath("$.sobrenome", is(empregadoAtualizado.getSobrenome())))
                .andExpect(jsonPath("$.email", is(empregadoAtualizado.getEmail())));;
    }


    @DisplayName("Teste para atualizar empregado por id não encontrado")
    @Test
    void testAtualizarEmpregadoPorIdNaoEncontrado() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

        Empregado empregadoAtualizado = Empregado.builder()
                .id(1L)
                .nome("Daniel Up")
                .sobrenome("Penelva Up")
                .email("d4n.andrade@gmail.com").build();

        given(empregadoServiceMock.getEmpregadoById(empregado.getId())).willReturn(Optional.empty());

        given(empregadoServiceMock.updateEmpregado(any(Empregado.class))).willAnswer((invocation) -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/empregados/{id}", empregado.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empregadoAtualizado)));

        //then
        response.andExpect(status().isNotFound())
        .andDo(print());
    }


    @DisplayName("Teste para deletar empregado por id")
    @Test
    void testDeletarEmpregadoPorId() throws Exception {

        // given
        Empregado empregado = criarEmpregado();

       doNothing().when(empregadoServiceMock).deleteEmpregado(empregado.getId());

        // when
        ResultActions response = mockMvc.perform(delete("/api/empregados/{id}", empregado.getId()));

        //then
        response.andExpect(status().isOk())
        .andDo(print());
    }
}
```

### Anotações JUnit Jupiter

Esta classe utiliza o framework de teste JUnit Jupiter e faz uso de várias anotações para configurar e executar os testes:

- **@WebMvcTest**: Essa anotação é usada para configurar o contexto de teste para testes de controlador. Ela permite que você teste as solicitações HTTP e as respostas do controlador sem iniciar um servidor HTTP completo.

### Métodos de Teste

A classe `EmpregadoControllerTest` contém vários métodos de teste, cada um focado em testar uma funcionalidade específica. Aqui estão os principais métodos de teste:

#### Teste para Criar um Empregado

- **Método**: `testCriarEmpregado`
- **Descrição**: Este teste verifica se é possível criar um novo empregado no sistema por meio de uma solicitação HTTP POST e se os dados do empregado criado correspondem aos dados fornecidos na solicitação.
- **Resultado Esperado**: O empregado é criado com sucesso, e seus dados correspondem aos fornecidos na solicitação.

#### Teste para Listar Empregados

- **Método**: `testListarEmpregados`
- **Descrição**: Este teste verifica se é possível listar todos os empregados cadastrados no sistema por meio de uma solicitação HTTP GET.
- **Resultado Esperado**: Uma lista contendo todos os empregados é retornada e possui o tamanho esperado.

#### Teste para Buscar Empregado por ID

- **Método**: `testBuscarEmpregadoPorId`
- **Descrição**: Este teste verifica se é possível buscar um empregado pelo seu ID por meio de uma solicitação HTTP GET.
- **Resultado Esperado**: O empregado é encontrado com sucesso.

#### Teste para Buscar Empregado por ID Não Encontrado

- **Método**: `testBuscarEmpregadoPorIdNãoEncontrado`
- **Descrição**: Este teste verifica se o sistema responde corretamente quando uma solicitação para buscar um empregado por ID não existente é feita por meio de uma solicitação HTTP GET.
- **Resultado Esperado**: O sistema responde com um status "Not Found" (não encontrado).

#### Teste para Atualizar Empregado por ID

- **Método**: `testAtualizarEmpregadoPorId`
- **Descrição**: Este teste verifica se é possível atualizar os dados de um empregado existente no sistema por meio de uma solicitação HTTP PUT e se os dados do empregado após a atualização correspondem aos dados fornecidos na solicitação.
- **Resultado Esperado**: O empregado é atualizado com sucesso, e seus dados correspondem aos fornecidos na solicitação.

#### Teste para Atualizar Empregado por ID Não Encontrado

- **Método**: `testAtualizarEmpregadoPorIdNaoEncontrado`
- **Descrição**: Este teste verifica se o sistema responde corretamente quando uma solicitação para atualizar um empregado por ID não existente é feita por meio de uma solicitação HTTP PUT.
- **Resultado Esperado**: O sistema responde com um status "Not Found" (não encontrado).

#### Teste para Deletar Empregado por ID

- **Método**: `testDeletarEmpregadoPorId`
- **Descrição**: Este teste verifica se é possível deletar um empregado pelo seu ID por meio de uma solicitação HTTP DELETE.
- **Resultado Esperado**: O empregado é deletado com sucesso.

### Uso da Classe

A classe `EmpregadoControllerTest` desempenha um papel fundamental na garantia da integridade e do funcionamento correto da camada de controle do sistema. Ela verifica se as operações de criação, listagem, busca, atualização e exclusão de empregados estão funcionando conforme o esperado, contribuindo para a robustez do sistema.


## Classe de Teste EmpregadoControllerTestRestTemplateTests

Este documento fornece uma visão geral da classe de teste `EmpregadoControllerTestRestTemplateTests`, que realiza testes de integração no controlador `EmpregadoController` usando o framework JUnit Jupiter, Spring Boot e o `TestRestTemplate`.

### Testes de Integração

Os testes de integração têm como objetivo verificar a interação completa de um sistema ou parte dele em um ambiente que simula o ambiente de produção. Na classe `EmpregadoControllerTestRestTemplateTests`, os testes se concentram em interagir com o controlador `EmpregadoController` por meio de solicitações HTTP e verificar as respostas.

```java
package com.daniel.testeunitario.controller;

import static org.junit.Assert.assertFalse;
// Adicionando import static
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.daniel.testeunitario.model.Empregado;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpregadoControllerTestRestTemplateTests {

    /* Vale ressaltar que para fazer esses testes de integração o Servidor precisa ficar startizado. */

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void testCriarEmpregado() {
        
        // Criar um objeto Empregado usando o padrão de construtor Builder
        Empregado empregado = Empregado.builder()
                .id(1L)
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();

        // Enviar uma solicitação HTTP POST para criar um empregado
        ResponseEntity<Empregado> resposta = testRestTemplate.postForEntity("http://localhost:8080/api/empregados", empregado, Empregado.class);

        // Verificar o código de status da resposta
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

        // Verificar o tipo de mídia (Content-Type) da resposta        
        assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());
        
        // Obter o empregado criado a partir do corpo da resposta
        Empregado empregadoCriado = resposta.getBody();

        // Verificar os campos do empregado criado
        assertNotNull(empregadoCriado);
        assertEquals(1L, empregadoCriado.getId());
        assertEquals("Daniel", empregadoCriado.getNome());
        assertEquals("Penelva", empregadoCriado.getSobrenome());
        assertEquals("d4n.andrade@gmail.com", empregadoCriado.getEmail());
    }

    @Test
    @Order(2)
    void testListarEmpregados() {
        
        // Enviar uma solicitação HTTP GET para listar todos os empregados
        ResponseEntity<Empregado[]> resposta = testRestTemplate.getForEntity("http://localhost:8080/api/empregados", Empregado[].class);

        // Converter o corpo da resposta em uma lista de Empregados
        List<Empregado> empregados = Arrays.asList(resposta.getBody());

        // Verificar o código de status da resposta
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        // Verificar o tipo de mídia (Content-Type) da resposta
        assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());

        // Verificar o tamanho da lista de empregados
        assertEquals(1, empregados.size());

        // Verificar os campos do primeiro empregado na lista
        assertEquals(1L,empregados.get(0).getId());
        assertEquals("Daniel", empregados.get(0).getNome());
        assertEquals("Penelva", empregados.get(0).getSobrenome());
        assertEquals("d4n.andrade@gmail.com", empregados.get(0).getEmail());
    }


    @Test
    @Order(3)
    void testObterEmpregadosPorId() {
        
        // Enviar uma solicitação HTTP GET para obter um empregado pelo ID 1
        ResponseEntity<Empregado> resposta = testRestTemplate.getForEntity("http://localhost:8080/api/empregados/1", Empregado.class);

        // Obter o empregado a partir do corpo da resposta
        Empregado empregado = resposta.getBody();

        // Verificar o código de status da resposta
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        // Verificar o tipo de mídia (Content-Type) da resposta
        assertEquals(MediaType.APPLICATION_JSON, resposta.getHeaders().getContentType());

        // Verificar se o objeto empregado não é nulo
        assertNotNull(empregado);

        // Verificar os campos do empregado obtido
        assertEquals(1L,empregado.getId());
        assertEquals("Daniel", empregado.getNome());
        assertEquals("Penelva", empregado.getSobrenome());
        assertEquals("d4n.andrade@gmail.com", empregado.getEmail());
    }

    @Test
    @Order(4)
    void testDeletarEmpregados() {

        // Enviar uma solicitação HTTP GET para listar todos os empregados
         ResponseEntity<Empregado[]> resposta = testRestTemplate.getForEntity("http://localhost:8080/api/empregados", Empregado[].class);

         // Converter o corpo da resposta em uma lista de Empregados
        List<Empregado> empregados = Arrays.asList(resposta.getBody());

        // Verificar se há inicialmente um empregado na lista
        assertEquals(1, empregados.size());

        // Criar um mapa de variáveis de caminho com o ID do empregado a ser excluído
        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id", 1L);

        // Enviar uma solicitação HTTP DELETE para excluir o empregado pelo ID
        ResponseEntity<Void> exchange = testRestTemplate
            .exchange("http://localhost:8080/api/empregados/{id}", HttpMethod.DELETE, null, Void.class, pathVariables);

        // Verificar o código de status da resposta após a exclusão bem-sucedida
        assertEquals(HttpStatus.OK, exchange.getStatusCode());

        // Verificar se a resposta não possui um corpo
        assertFalse(exchange.hasBody());

        // Enviar uma nova solicitação HTTP GET para listar todos os empregados após a exclusão
        resposta = testRestTemplate.getForEntity("http://localhost:8080/api/empregados", Empregado[].class);

        // Converter o corpo da resposta em uma lista de Empregados novamente
        empregados = Arrays.asList(resposta.getBody());

        // Verificar se a lista de empregados está vazia após a exclusão
        assertEquals(0, empregados.size());

        // Enviar uma solicitação HTTP GET para obter detalhes do empregado excluído (ID 2, que não existe mais)
        ResponseEntity<Empregado> respostaDetalhe = testRestTemplate.getForEntity("http://localhost:8080/api/empregados/2", Empregado.class);

        // Verificar o código de status da resposta após tentar obter detalhes do empregado excluído
        assertEquals(HttpStatus.NOT_FOUND, respostaDetalhe.getStatusCode());

        // Verificar se a resposta não possui um corpo
        assertFalse(respostaDetalhe.hasBody());
    }
}
```

### Anotações JUnit Jupiter e Spring Boot

Esta classe utiliza o framework de teste JUnit Jupiter em conjunto com o Spring Boot para configurar o ambiente de teste. Aqui estão algumas das principais anotações usadas:

- **@TestMethodOrder(MethodOrderer.OrderAnnotation.class)**: Essa anotação especifica a ordem de execução dos métodos de teste com base em anotações `@Order`.

- **@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)**: Esta anotação indica que os testes estão sendo executados em um ambiente Spring Boot com um servidor incorporado em uma porta aleatória.

### Métodos de Teste de Integração

A classe `EmpregadoControllerTestRestTemplateTests` contém vários métodos de teste de integração, cada um focado em testar uma funcionalidade específica do controlador `EmpregadoController`. Aqui estão os principais métodos de teste:

#### Teste de Criar um Empregado

- **Método**: `testCriarEmpregado`
- **Descrição**: Este teste verifica se é possível criar um novo empregado no sistema por meio do endpoint `POST` do controlador. Ele envia uma solicitação HTTP POST com um objeto de empregado e verifica se a resposta está correta.
- **Resultado Esperado**: O empregado é criado com sucesso, e os detalhes do empregado criado na resposta correspondem aos detalhes fornecidos na solicitação.

#### Teste de Listar Empregados

- **Método**: `testListarEmpregados`
- **Descrição**: Este teste verifica se é possível listar todos os empregados cadastrados no sistema por meio do endpoint `GET` do controlador. Ele verifica o código de status da resposta e os detalhes dos empregados listados.
- **Resultado Esperado**: A lista de empregados é retornada com sucesso, e os detalhes dos empregados listados correspondem aos empregados cadastrados.

#### Teste de Obter Empregado por ID

- **Método**: `testObterEmpregadosPorId`
- **Descrição**: Este teste verifica se é possível obter os detalhes de um empregado específico pelo seu ID por meio do endpoint `GET` do controlador. Ele verifica o código de status da resposta e os detalhes do empregado obtido.
- **Resultado Esperado**: Os detalhes do empregado são obtidos com sucesso, e correspondem aos detalhes do empregado especificado pelo ID.

#### Teste de Deletar Empregados

- **Método**: `testDeletarEmpregados`
- **Descrição**: Este teste verifica se é possível excluir um empregado existente no sistema por meio do endpoint `DELETE` do controlador. Ele verifica o código de status da resposta após a exclusão e se o empregado não existe mais após a exclusão.
- **Resultado Esperado**: O empregado é excluído com sucesso, e uma tentativa de obter detalhes do empregado excluído retorna um código de status `NOT_FOUND`.

### Observações

- Certifique-se de que o servidor esteja em execução para executar os testes de integração.
- A porta `8080` é usada nas URLs de teste; ajuste-a conforme necessário com base na configuração do seu servidor.
- Esses testes interagem com o sistema como um todo, incluindo o controlador e o serviço subjacente, garantindo a integridade das operações.
- Os testes de integração são uma parte importante da garantia de qualidade de um aplicativo, pois verificam se as várias partes do aplicativo estão funcionando bem juntas.

## Classe de Teste EmpregadoControllerWebTestClientTests

Este documento fornece uma visão geral da classe de teste `EmpregadoControllerWebTestClientTests`, que realiza testes de integração no controlador `EmpregadoController` usando o `WebTestClient`. Essa classe é responsável por verificar se as operações da API REST são executadas corretamente.

### Testes de Integração com WebTestClient

Os testes de integração com o `WebTestClient` simulam chamadas HTTP para o controlador e verificam as respostas recebidas. Cada método de teste corresponde a uma operação diferente no controlador.

```java
package com.daniel.testeunitario.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import java.util.List;
import static org.hamcrest.Matchers.*;
import com.daniel.testeunitario.model.Empregado;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmpregadoControllerWebTestClientTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void testCriarEmpregado() {
        
        // Dado (given): Você está configurando o estado inicial
        // Crie um objeto Empregado usando o padrão de construtor Builder
        Empregado empregado = Empregado.builder()
                .id(3L)
                .nome("Daniel Up")
                .sobrenome("Penelva Up")
                .email("d4n.penelva@gmail.com").build();

        // Quando (when): Você executa a ação que deseja testar
        // Envie uma solicitação HTTP POST usando o WebTestClient
        webTestClient.post().uri("http://localhost:8080/api/empregados")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(empregado)
            .exchange()
        // Então (then): Você verifica os resultados ou o comportamento esperado
            .expectStatus().isCreated() // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON) // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBody() // Verifique o corpo da resposta JSON
            .jsonPath("$.id").isEqualTo(empregado.getId())
            .jsonPath("$.nome").isEqualTo(empregado.getNome())
            .jsonPath("$.sobrenome").isEqualTo(empregado.getSobrenome())
            .jsonPath("$.email").isEqualTo(empregado.getEmail());
    }

    @Test
    @Order(2)
    void testBsucarEmpregadoPorId() {
    
      // Envie uma solicitação HTTP GET para obter um empregado pelo ID 1 (.exchange() - envia o request)
      webTestClient.get().uri("http://localhost:8080/api/empregados/3").exchange()
            .expectStatus().isOk() // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON) // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBody() // Verifique o corpo da resposta JSON
            .jsonPath("$.id").isEqualTo(3)
            .jsonPath("$.nome").isEqualTo("Daniel Up")
            .jsonPath("$.sobrenome").isEqualTo("Penelva Up")
            .jsonPath("$.email").isEqualTo("d4n.penelva@gmail.com");
    }

    @Test
    @Order(3)
    void testListarEmpregado() {
    
      // Envie uma solicitação HTTP GET para listar todos os empregados
      webTestClient.get().uri("http://localhost:8080/api/empregados").exchange()
            .expectStatus().isOk()  // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON)  // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBody()  // Verifique o corpo da resposta JSON
            .jsonPath("$[1].nome").isEqualTo("Daniel Up")  // Verifique o campo "nome" do primeiro elemento da matriz JSON
            .jsonPath("$[1].sobrenome").isEqualTo("Penelva Up")  // Verifique o campo "sobrenome" do primeiro elemento da matriz JSON
            .jsonPath("$[1].email").isEqualTo("d4n.penelva@gmail.com")  // Verifique o campo "email" do primeiro elemento da matriz JSON
            .jsonPath("$").isArray()   // Verifique se a resposta é uma matriz JSON
            .jsonPath("$").value(hasSize(2));  // Verifique o tamanho da matriz JSON (quantidade de elementos)
    }

    @Test
    @Order(4)
    void testObterListarEmpregado() {
    
     // Envie uma solicitação HTTP GET para listar todos os empregados
      webTestClient.get().uri("http://localhost:8080/api/empregados").exchange()   
            .expectStatus().isOk()  // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON)  // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBodyList(Empregado.class)  // Verifique o corpo da resposta JSON como uma lista de objetos Empregado
            .consumeWith(response -> {  // Consuma a resposta e realize verificações adicionais
                List<Empregado> empregados = response.getResponseBody();
                Assertions.assertEquals(2, empregados.size());   // Verifique se a lista de empregados possui tamanho igual a 1
                Assertions.assertNotNull(empregados);  // Verifique se a lista de empregados não é nula
            });
    }

    @Test
    @Order(5)
    void testAtualizarEmpregado() {

        // Criar um objeto Empregado com os dados atualizados
        Empregado empregadoAtualizado = Empregado.builder()
                .nome("Davi")
                .sobrenome("Silva")
                .email("davi@gmail.com").build();

         // Enviar uma solicitação HTTP PUT para atualizar o empregado com ID 1
         webTestClient.put().uri("http://localhost:8080/api/empregados/2")
                .contentType(MediaType.APPLICATION_JSON)  // Definir o tipo de mídia como JSON
                .bodyValue(empregadoAtualizado)  // Enviar o objeto empregadoAtualizado no corpo da solicitação
                .exchange()  // Executar a solicitação
                .expectStatus().isOk()  // Verificar o código de status da resposta
                .expectHeader().contentType(MediaType.APPLICATION_JSON);  // Verificar o cabeçalho de tipo de mídia (Content-Type) da resposta
    }

    @Test
    @Order(6)
    void testDeletarEmpregado() {

        // Primeiro, verifica se o empregado com ID 1 existe
         webTestClient.get().uri("http://localhost:8080/api/empregados")
                .exchange()
                .expectStatus().isOk()  
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Empregado.class)
                .hasSize(2); 
             
        // Em seguida, envia uma solicitação HTTP DELETE para excluir o empregado com ID 1
         webTestClient.delete().uri("http://localhost:8080/api/empregados/2")
                .exchange()
                .expectStatus().isOk();
      
        // Após a exclusão, verifica se não há mais empregados (espera-se que a lista esteja vazia)
         webTestClient.get().uri("http://localhost:8080/api/empregados").exchange()
                .expectStatus().isOk()  
                .expectHeader().contentType(MediaType.APPLICATION_JSON)  
                .expectBodyList(Empregado.class)
                .hasSize(1);

         // Finalmente, tenta buscar o empregado com ID 1 novamente, o que deve resultar em um erro 4xx
         webTestClient.get().uri("http://localhost:8080/api/empregados/1").exchange()
                .expectStatus()
                .is4xxClientError(); 
    }
}
```

### Anotações JUnit Jupiter e Spring Boot

A classe `EmpregadoControllerWebTestClientTests` utiliza o framework de teste JUnit Jupiter em conjunto com o Spring Boot para configurar o ambiente de teste. Aqui estão algumas das principais anotações usadas:

- **@TestMethodOrder(MethodOrderer.OrderAnnotation.class)**: Essa anotação especifica a ordem de execução dos métodos de teste com base em anotações `@Order`.

- **@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)**: Esta anotação indica que os testes estão sendo executados em um ambiente Spring Boot com um servidor incorporado em uma porta aleatória.

### Métodos de Teste de Integração com WebTestClient

A classe `EmpregadoControllerWebTestClientTests` contém vários métodos de teste de integração, cada um focado em testar uma funcionalidade específica do controlador `EmpregadoController`. Aqui estão os principais métodos de teste:

#### Teste de Criar um Empregado

- **Método**: `testCriarEmpregado`
- **Descrição**: Este teste verifica se é possível criar um novo empregado no sistema por meio do endpoint `POST` do controlador. Ele envia uma solicitação HTTP POST com um objeto de empregado e verifica se a resposta está correta.
- **Resultado Esperado**: O empregado é criado com sucesso, e os detalhes do empregado criado na resposta correspondem aos detalhes fornecidos na solicitação.

#### Teste de Buscar Empregado por ID

- **Método**: `testBsucarEmpregadoPorId`
- **Descrição**: Este teste verifica se é possível obter os detalhes de um empregado específico pelo seu ID por meio do endpoint `GET` do controlador. Ele verifica o código de status da resposta e os detalhes do empregado obtido.
- **Resultado Esperado**: Os detalhes do empregado são obtidos com sucesso, e correspondem aos detalhes do empregado especificado pelo ID.

#### Teste de Listar Empregados

- **Método**: `testListarEmpregado`
- **Descrição**: Este teste verifica se é possível listar todos os empregados cadastrados no sistema por meio do endpoint `GET` do controlador. Ele verifica o código de status da resposta e os detalhes dos empregados listados.
- **Resultado Esperado**: A lista de empregados é retornada com sucesso, e os detalhes dos empregados listados correspondem aos empregados cadastrados.

#### Teste de Obter Lista de Empregados

- **Método**: `testObterListarEmpregado`
- **Descrição**: Este teste verifica se é possível listar todos os empregados cadastrados no sistema por meio do endpoint `GET` do controlador e converter a resposta em uma lista de objetos `Empregado`. Ele verifica o código de status da resposta e a validade da lista resultante.
- **Resultado Esperado**: A lista de empregados é retornada com sucesso e contém a quantidade correta de elementos. A lista de empregados não é nula.

#### Teste de Atualizar Empregado

- **Método**: `testAtualizarEmpregado`
- **Descrição**: Este teste verifica se é possível atualizar os detalhes de um empregado existente no sistema por meio do endpoint `PUT` do controlador. Ele envia uma solicitação HTTP PUT com um objeto de empregado atualizado e verifica se a resposta está correta.
- **Resultado Esperado**: O empregado é atualizado com sucesso, e os detalhes do empregado na resposta correspondem aos detalhes atualizados.

#### Teste de Deletar Empregado

- **Método**: `testDeletarEmpregado`
- **Descrição**: Este teste verifica se é possível excluir um empregado existente no sistema por meio do endpoint `DELETE` do controlador. Ele verifica o código de status da resposta após a exclusão e se o empregado não existe mais após a exclusão.
- **Resultado Esperado**: O empregado é excluído com sucesso, e uma tentativa de buscar o empregado novamente retorna um código de erro.

# Conclusão

Neste projeto, foi desenvolvido uma aplicação de exemplo para gerenciar informações de empregados. A aplicação segue uma arquitetura de camadas com modelos, serviços e controladores, fornecendo endpoints RESTful para realizar operações CRUD (Create, Read, Update, Delete) em registros de empregados.

Aqui está um resumo das principais componentes e funcionalidades deste projeto:

## Camada de Modelo

- `Empregado`: Esta classe de modelo representa um empregado com atributos como `id`, `nome`, `sobrenome` e `email`. É usado para representar os dados dos empregados.

## Camada de Serviço

- `EmpregadoService`: A classe `EmpregadoService` fornece métodos para realizar operações relacionadas a empregados, como criar, listar, buscar por ID, atualizar e excluir empregados. Ele faz uso do repositório `EmpregadoRepository` para acessar os dados.

## Camada de Controlador

- `EmpregadoController`: O controlador `EmpregadoController` expõe endpoints RESTful para interagir com os dados dos empregados. Ele lida com solicitações HTTP, chama os métodos apropriados do serviço `EmpregadoService` e retorna respostas adequadas.

## Testes

- Foram desenvolvidos testes unitários para a classe `EmpregadoService` usando o framework JUnit e o framework de mocking Mockito. Esses testes verificam se os métodos do serviço funcionam conforme o esperado, incluindo casos de sucesso e exceções.

- Também foram criados testes de integração para o controlador `EmpregadoController` usando o `WebTestClient`. Esses testes simulam chamadas HTTP para os endpoints RESTful e verificam se as operações da API são executadas corretamente.

O projeto como um todo demonstra a implementação de uma aplicação Spring Boot com uma arquitetura de camadas bem estruturada, que é comum em muitos aplicativos da vida real.

Esperamos que este projeto e sua documentação tenham sido úteis para entender como desenvolver uma aplicação Java Spring Boot e como criar testes unitários e de integração para garantir o funcionamento correto da aplicação.

---

## **Autor:** `Daniel Penelva de Andrade`




