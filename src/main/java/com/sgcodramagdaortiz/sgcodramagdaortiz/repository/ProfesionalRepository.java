package com.sgcodramagdaortiz.sgcodramagdaortiz.repository;

// Importa JpaRepository, que ya trae implementados
// los métodos CRUD básicos (findAll, findById, save, deleteById, etc.)
import org.springframework.data.jpa.repository.JpaRepository;

// Importa Optional para manejar resultados que pueden no existir
import java.util.Optional;

// Importa la entidad Profesional
import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Profesional;

/**
 * ============================================================
 * PROFESIONALREPOSITORY.JAVA
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Repositorio de acceso a datos para la entidad Profesional.
 *
 * Al extender JpaRepository<Profesional, Long>, Spring Data
 * JPA genera automáticamente la implementación de los
 * métodos CRUD básicos (findAll, findById, save, deleteById,
 * existsById, etc.), sin necesidad de escribir SQL manualmente.
 */
public interface ProfesionalRepository extends JpaRepository<Profesional, Long> {

    /**
     * Busca un profesional por su número de identificación.
     * Spring Data JPA genera automáticamente la consulta
     * a partir del nombre del método.
     *
     * @param identificacion número de identificación
     * @return profesional encontrado, si existe
     */
    Optional<Profesional> findByIdentificacion(String identificacion);

    /**
     * Verifica si ya existe un profesional con el correo indicado.
     * Útil para validar duplicados antes de guardar.
     *
     * @param correo correo a verificar
     * @return true si ya existe un profesional con ese correo
     */
    boolean existsByCorreo(String correo);

}