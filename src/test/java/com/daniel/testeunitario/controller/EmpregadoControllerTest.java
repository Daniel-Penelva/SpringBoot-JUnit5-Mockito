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
import static org.mockito.BDDMockito.given; //given
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; //solicitações HTTP, como GET, POST, PUT, DELETE, entre outros
import static org.hamcrest.CoreMatchers.is; // Teste - verificar se os resultados obtidos são iguais aos resultados esperados.
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // usado para importar vários métodos estáticos da classe `MockMvcResultMatchers`

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*; // métodos são usados para adicionar manipuladores de resultados às suas solicitações simuladas com `MockMvc`

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
