package com.breno.consultaendereco.entitie;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Endereco {
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private double frete;


    public static class Builder {
        private final Endereco endereco;

        public Builder() {
            endereco = new Endereco();
        }

        public Builder cep(String cep) {
            endereco.cep = cep;
            return this;
        }

        public Builder rua(String rua) {
            endereco.rua = rua;
            return this;
        }

        public Builder bairro(String bairro) {
            endereco.bairro = bairro;
            return this;
        }

        public Builder cidade(String cidade) {
            endereco.cidade = cidade;
            return this;
        }

        public Builder estado(String estado) {
            endereco.estado = estado;
            return this;
        }

        public Builder complemento(String complemento) {
            endereco.complemento = complemento;
            return this;
        }

        public Builder frete(double frete) {
            endereco.frete = frete;
            return this;
        }

        public Endereco build() {
            return endereco;
        }
    }

    // getters e setters
}
