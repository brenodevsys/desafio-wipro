package com.breno.consultaendereco.controller;

import com.breno.consultaendereco.entitie.Endereco;
import com.breno.consultaendereco.exceptions.EnderecoNaoEncontradoException;
import com.breno.consultaendereco.service.EnderecoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/consulta-endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @ApiOperation(value="Consulta endereço por CEP", notes = "Consulta endereço por CEP")
    @PostMapping
    public ResponseEntity<Endereco> consultaEndereco(
            @ApiParam(value = "CEP", example = "01001000") @RequestBody ConsultaEnderecoRequest request) {
        Endereco endereco = enderecoService.buscarEnderecoPorCep(request.getCep());
        return ResponseEntity.ok(endereco);
    }

    @ExceptionHandler(EnderecoNaoEncontradoException.class)
    public ResponseEntity<String> handleEnderecoNaoEncontradoException(EnderecoNaoEncontradoException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
