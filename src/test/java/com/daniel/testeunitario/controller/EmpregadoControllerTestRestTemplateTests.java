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
