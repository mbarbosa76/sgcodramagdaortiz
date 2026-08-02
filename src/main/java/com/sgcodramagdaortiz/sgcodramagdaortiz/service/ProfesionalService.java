package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

// Importa List y Optional
import java.util.List;
import java.util.Optional;

// Importa la entidad Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

/**
 * ============================================================
 * PROFESIONALSERVICE.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Interfaz que define las operaciones disponibles
 * para el módulo Profesional.
 *
 * La implementación se encuentra en:
 *
 * ProfesionalServiceImpl.java
 *
 * Se sigue el mismo patrón (interfaz + implementación)
 * que ya se usa en el módulo Usuario
 * (UsuarioService / UsuarioServiceImpl).
 */
public interface ProfesionalService {

    List<Profesional> listarProfesionales();

    Optional<Profesional> buscarProfesionalPorId(Long idProfesional);

    Profesional guardarProfesional(Profesional profesional);

    Optional<Profesional> actualizarProfesional(Long idProfesional, Profesional profesional);

    boolean eliminarProfesional(Long idProfesional);

}
