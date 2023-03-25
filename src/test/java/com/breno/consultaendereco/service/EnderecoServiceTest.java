package com.breno.consultaendereco.service;

import com.breno.consultaendereco.entitie.Endereco;
import com.breno.consultaendereco.entitie.EnderecoViaCep;
import com.breno.consultaendereco.exceptions.EnderecoNaoEncontradoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class EnderecoServiceTest {
    private EnderecoService enderecoService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        enderecoService = new EnderecoService(restTemplate);
    }

    @Test
    void deveBuscarEnderecoPorCep() throws EnderecoNaoEncontradoException {
        EnderecoViaCep enderecoViaCep = new EnderecoViaCep();
        enderecoViaCep.setBairro("Valparaiso");
        enderecoViaCep.setCep("72876128");
        enderecoViaCep.setLocalidade("Valparaiso de Goias");
        enderecoViaCep.setComplemento("12");
        enderecoViaCep.setUf("GO");
        enderecoViaCep.setLogradouro("Etapa B");


        Mockito.when(restTemplate.getForObject("https://viacep.com.br/ws/12345678/json", EnderecoViaCep.class))
                .thenReturn(enderecoViaCep);

        Endereco endereco = enderecoService.buscarEnderecoPorCep("12345678");


        Assertions.assertEquals("Valparaiso", endereco.getBairro());
        Assertions.assertEquals("72876128", endereco.getCep());
        Assertions.assertEquals("Valparaiso de Goias", endereco.getCidade());
        Assertions.assertEquals("12", endereco.getComplemento());
        Assertions.assertEquals("GO", endereco.getEstado());
        Assertions.assertEquals("Etapa B", endereco.getRua());
    }
}
