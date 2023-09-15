package com.daniel.testeunitario.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

// Adicionando imports static
import static org.springframework.boot.test.context.SpringBootTest.*;
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
    
}
