package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}