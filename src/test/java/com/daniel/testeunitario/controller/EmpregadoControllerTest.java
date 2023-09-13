package com.daniel.testeunitario.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

//Adicionando essas importações static
import static org.mockito.BDDMockito.given; //given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //solicitações HTTP, como GET, POST, PUT, DELETE, entre outros
import static org.hamcrest.CoreMatchers.is; // Teste - verificar se os resultados obtidos são iguais aos resultados esperados.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // usado para importar vários métodos estáticos da classe `MockMvcResultMatchers`

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*; // métodos são usados para adicionar manipuladores de resultados às suas solicitações simuladas com `MockMvc`

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.daniel.testeunitario.model.Empregado;
import com.daniel.testeunitario.service.EmpregadoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
public class EmpregadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpregadoService empregadoServiceMock;

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

        /* Para didática: Ou podemos chamar pela classe 'MockMvcRequestBuilders' o método post(), ao invés de utilizar o import static:
        'import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;'

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/empregados")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(empregado)));
        */

        // then
        response.andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(empregado.getNome())))
                .andExpect(jsonPath("$.sobrenome", is(empregado.getSobrenome())))
                .andExpect(jsonPath("$.email", is(empregado.getEmail())));
    }

}
