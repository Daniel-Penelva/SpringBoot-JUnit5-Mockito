package com.daniel.testeunitario.controller;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

// Adicionando import static
import static org.junit.jupiter.api.Assertions.*;

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

}
