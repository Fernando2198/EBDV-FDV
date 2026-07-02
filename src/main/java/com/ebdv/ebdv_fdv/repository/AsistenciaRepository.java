package com.ebdv.ebdv_fdv.repository;

import com.ebdv.ebdv_fdv.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByNinoId(Long ninoId);
    Optional<Asistencia> findByNinoIdAndDiaSemana(Long ninoId, String diaSemana);

}
