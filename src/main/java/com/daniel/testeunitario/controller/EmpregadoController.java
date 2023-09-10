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

    // Listar todos os empregados - - http://localhost:8080/api/empregados
    @GetMapping
    public List<Empregado> findAllEmpregados() {
        return empregadoService.getAllEmpregados();
    }

    /* Analisando buscar empregado por id:
    * 1. @GetMapping("/{id}"): Esta anotação mapeia uma solicitação HTTP GET para o endpoint representado por /{id}. O valor {id} é uma variável 
    *    de caminho que captura o valor do id da URL e o torna disponível como um parâmetro para o método.
    *
    * 2. public ResponseEntity<Empregado> findByIdEmpregado(@PathVariable Long id): Este é o método que será executado quando a solicitação GET 
    *    for recebida. Ele recebe o id da solicitação como um parâmetro de caminho, que é anotado com @PathVariable. Isso significa que o valor 
    *    do id na URL será vinculado ao parâmetro id deste método.
    *
    * 3. empregadoService.getEmpregadoById(id): Este método chama getEmpregadoById do serviço empregadoService, passando o id recebido como 
    *    parâmetro. Presumivelmente, este método do serviço busca um empregado pelo ID no banco de dados ou em outra fonte de dados.
    *
    * 4. .map(ResponseEntity::ok): Se o empregadoService.getEmpregadoById(id) retornar um Empregado, este trecho mapeia esse empregado em um 
    *     ResponseEntity com status 200 (OK). ResponseEntity::ok é uma referência a um construtor estático fornecido pelo Spring que cria um 
    *     ResponseEntity com o status OK e o corpo definido como o objeto empregado retornado.
    
    * 5 .orElseGet(() -> ResponseEntity.notFound().build()): Se empregadoService.getEmpregadoById(id) retornar um valor vazio (ou seja, nenhum 
    *    empregado correspondente ao ID foi encontrado), este trecho gera uma resposta com status 404 (Not Found). ResponseEntity.notFound().build() 
    *    cria um ResponseEntity com status 404 e corpo vazio.
    *
    * http://localhost:8080/api/empregados/{id}
    **/
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
