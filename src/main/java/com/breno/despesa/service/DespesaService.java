package com.breno.despesa.service;

import com.breno.despesa.entitie.Despesa;
import com.breno.despesa.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DespesaService {
    private DespesaRepository despesaRepository;

    @Autowired
    public DespesaService(DespesaRepository despesaRepository) {
        this.despesaRepository = despesaRepository;
    }

    @Transactional
    public List<Despesa> getAllDespesas() {
        return despesaRepository.findAll();
    }

    @Transactional
    public Despesa getDespesaById(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    @Transactional
    public Despesa createDespesa(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    @Transactional
    public Despesa updateDespesa(Long id, Despesa despesa) {
        Despesa existingDespesa = despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        existingDespesa.setAnoMovimentacao(despesa.getAnoMovimentacao());
        existingDespesa.setMesMovimentacao(despesa.getMesMovimentacao());
        existingDespesa.setOrgaoNome(despesa.getOrgaoNome());
        existingDespesa.setValorPago(despesa.getValorPago());
        existingDespesa.setCategoria(despesa.getCategoria());

        return despesaRepository.save(existingDespesa);
    }

    @Transactional
    public void deleteDespesa(Long id) {
        despesaRepository.deleteById(id);
    }

    @Transactional
    public Map<Integer, BigDecimal> getDespesasTotaisPorMes() {
        List<Despesa> despesas = despesaRepository.findAll();

        return despesas.stream()
                .collect(Collectors.groupingBy(Despesa::getMesMovimentacao,
                        Collectors.mapping(despesa -> parseValorPago(despesa.getValorPago()), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Transactional
    public Map<String, BigDecimal> getDespesasTotaisPorCategoria() {
        List<Despesa> despesas = despesaRepository.findAll();

        return despesas.stream()
                .collect(Collectors.groupingBy(Despesa::getCategoria,
                        Collectors.mapping(despesa -> parseValorPago(despesa.getValorPago()), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    @Transactional
    public Map<String, BigDecimal> getDespesasPorOrgaoNome() {
        List<Despesa> despesas = despesaRepository.findAll();

        return despesas.stream()
                .collect(Collectors.groupingBy(Despesa::getOrgaoNome,
                        Collectors.mapping(despesa -> parseValorPago(despesa.getValorPago()), Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }
    private BigDecimal parseValorPago(String valorPago) {
        String valorFormatado = valorPago.replace(",", ".");
        return new BigDecimal(valorFormatado);
    }
}
