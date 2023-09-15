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
                .id(1L)
                .nome("Daniel")
                .sobrenome("Penelva")
                .email("d4n.andrade@gmail.com").build();

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
    
      // Envie uma solicitação HTTP GET para obter um empregado pelo ID 1
      webTestClient.get().uri("http://localhost:8080/api/empregados/1").exchange()
            .expectStatus().isOk() // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON) // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBody() // Verifique o corpo da resposta JSON
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.nome").isEqualTo("Daniel")
            .jsonPath("$.sobrenome").isEqualTo("Penelva")
            .jsonPath("$.email").isEqualTo("d4n.andrade@gmail.com");
    }

    @Test
    @Order(3)
    void testListarEmpregado() {
    
      // Envie uma solicitação HTTP GET para listar todos os empregados
      webTestClient.get().uri("http://localhost:8080/api/empregados").exchange()
            .expectStatus().isOk()  // Verifique o código de status da resposta
            .expectHeader().contentType(MediaType.APPLICATION_JSON)  // Verifique o cabeçalho de tipo de mídia (Content-Type) da resposta
            .expectBody()  // Verifique o corpo da resposta JSON
            .jsonPath("$[0].nome").isEqualTo("Daniel")  // Verifique o campo "nome" do primeiro elemento da matriz JSON
            .jsonPath("$[0].sobrenome").isEqualTo("Penelva")  // Verifique o campo "sobrenome" do primeiro elemento da matriz JSON
            .jsonPath("$[0].email").isEqualTo("d4n.andrade@gmail.com")  // Verifique o campo "email" do primeiro elemento da matriz JSON
            .jsonPath("$").isArray()   // Verifique se a resposta é uma matriz JSON
            .jsonPath("$").value(hasSize(1));  // Verifique o tamanho da matriz JSON (quantidade de elementos)
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
                Assertions.assertEquals(1, empregados.size());   // Verifique se a lista de empregados possui tamanho igual a 1
                Assertions.assertNotNull(empregados);  // Verifique se a lista de empregados não é nula
            });
    }
    
}
