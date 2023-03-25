package com.breno.consultaendereco.controller;

import com.breno.consultaendereco.entitie.Endereco;
import com.breno.consultaendereco.entitie.EnderecoViaCep;
import com.breno.consultaendereco.exceptions.EnderecoNaoEncontradoException;
import com.breno.consultaendereco.service.EnderecoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class EnderecoControllerTest {
    @InjectMocks
    private EnderecoController enderecoController;

    @Mock
    private EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testConsultaEndereco() throws EnderecoNaoEncontradoException {

        ConsultaEnderecoRequest request = new ConsultaEnderecoRequest();
        request.setCep("72876128");
        EnderecoViaCep enderecoViaCep = mockEnderecoViaCep();
        Endereco endereco = new Endereco.Builder()
                .bairro(enderecoViaCep.getBairro())
                .cep(enderecoViaCep.getCep())
                .cidade(enderecoViaCep.getLocalidade())
                .complemento(enderecoViaCep.getComplemento())
                .estado(enderecoViaCep.getUf())
                .rua(enderecoViaCep.getLogradouro())
                .frete(12.5)
                .build();
        when(enderecoService.buscarEnderecoPorCep(request.getCep())).thenReturn(endereco);


        ResponseEntity<Endereco> resultado = enderecoController.consultaEndereco(request);


        assertEquals(HttpStatus.OK, resultado.getStatusCode());
        assertEquals(endereco, resultado.getBody());
    }


    private EnderecoViaCep mockEnderecoViaCep() {
        EnderecoViaCep enderecoViaCep = new EnderecoViaCep();
        enderecoViaCep.setLogradouro("Quadra 34");
        enderecoViaCep.setUf("GO");
        enderecoViaCep.setCep("72876128");
        enderecoViaCep.setBairro("Valparaiso 1");
        enderecoViaCep.setGia("Gia");
        enderecoViaCep.setIbge("Ibge");
        enderecoViaCep.setUnidade("Unidade");
        enderecoViaCep.setComplemento("Complemento");
        enderecoViaCep.setLocalidade("Valparaiso de Goiás");
        return enderecoViaCep;
    }
}
