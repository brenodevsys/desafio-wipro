package com.breno.despesa.repository;

import com.breno.despesa.entitie.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
}
