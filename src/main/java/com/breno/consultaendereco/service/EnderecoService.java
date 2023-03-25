package com.breno.consultaendereco.service;

import com.breno.consultaendereco.entitie.Endereco;
import com.breno.consultaendereco.entitie.EnderecoViaCep;
import com.breno.consultaendereco.exceptions.EnderecoNaoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class EnderecoService {

    private static final double FRETE_SUDESTE = 7.85;
    private static final double FRETE_CENTRO_OESTE = 12.5;
    private static final double FRETE_NORDESTE = 15.98;
    private static final double FRETE_SUL = 17.3;
    private static final double FRETE_NORTE = 20.83;


    private final RestTemplate restTemplate;

    @Autowired
    public EnderecoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Endereco buscarEnderecoPorCep(String cep) throws EnderecoNaoEncontradoException {
        EnderecoViaCep enderecoViaCep = restTemplate.getForObject("https://viacep.com.br/ws/"+cep+"/json", EnderecoViaCep.class);
        if (enderecoViaCep.getCep() == null) {
            throw new EnderecoNaoEncontradoException("Endereço não encontrado para o CEP informado: " + cep);
        }
        Endereco.Builder builder = new Endereco.Builder()
                .bairro(enderecoViaCep.getBairro())
                .cep(enderecoViaCep.getCep())
                .cidade(enderecoViaCep.getLocalidade())
                .complemento(enderecoViaCep.getComplemento())
                .estado(enderecoViaCep.getUf())
                .rua(enderecoViaCep.getLogradouro())
                .frete(calcularFrete(enderecoViaCep.getUf()));

        return builder.build();
    }


    public double calcularFrete(String estado) {

        switch (estado) {
            case "SP":
            case "RJ":
            case "ES":
            case "MG":
                return FRETE_SUDESTE;
            case "DF":
            case "GO":
            case "MS":
            case "MT":
                return FRETE_CENTRO_OESTE;
            case "AL":
            case "BA":
            case "CE":
            case "MA":
            case "PB":
            case "PE":
            case "PI":
            case "RN":
            case "SE":
                return FRETE_NORDESTE;
            case "PR":
            case "RS":
            case "SC":
                return FRETE_SUL;
            case "AC":
            case "AP":
            case "AM":
            case "PA":
            case "RO":
            case "RR":
            case "TO":
                return FRETE_NORTE;
            default:
                return 0.0;
        }
    }
}



