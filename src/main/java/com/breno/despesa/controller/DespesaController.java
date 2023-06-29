package com.breno.despesa.controller;

import com.breno.despesa.entitie.Despesa;
import com.breno.despesa.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/despesas")
public class DespesaController {
    private DespesaService despesaService;

    @Autowired
    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    /**@GetMapping
    public List<Despesa> getAllDespesas() {
        return despesaService.getAllDespesas();
    } **/ //método comentado devido requisição tentar buscar mais de 94mil tipos de despesas do json, verificar futura melhoria de busca.

    @GetMapping("/{id}")
    public Despesa getDespesaById(@PathVariable Long id) {
        return despesaService.getDespesaById(id);
    }

    @PostMapping
    public Despesa createDespesa(@RequestBody Despesa despesa) {
        return despesaService.createDespesa(despesa);
    }

    @PutMapping("/{id}")
    public Despesa updateDespesa(@PathVariable Long id, @RequestBody Despesa despesa) {
        return despesaService.updateDespesa(id, despesa);
    }

    @DeleteMapping("/{id}")
    public void deleteDespesa(@PathVariable Long id) {
        despesaService.deleteDespesa(id);
    }

    @GetMapping("/despesas-por-mes")
    public Map<Integer, BigDecimal> getDespesasTotaisPorMes() {
        return despesaService.getDespesasTotaisPorMes();
    }

    @GetMapping("/despesas-por-categoria")
    public Map<String, BigDecimal> getDespesasTotaisPorCategoria() {
        return despesaService.getDespesasTotaisPorCategoria();
    }

    @GetMapping("/despesas-por-orgao")
    public Map<String, BigDecimal> getDespesasPorOrgaoNome() {
        return despesaService.getDespesasPorOrgaoNome();
    }
}

