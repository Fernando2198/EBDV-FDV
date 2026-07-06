package com.ebdv.ebdv_fdv.repository;

import com.ebdv.ebdv_fdv.model.Asistencia;
import com.ebdv.ebdv_fdv.model.Nino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {
    List<Asistencia> findByNinoId(Long ninoId);
    Optional<Asistencia> findByNinoIdAndDiaSemana(Long ninoId, String diaSemana);
    Optional<Asistencia> findByNinoAndDiaSemana(Nino nino, String diaSemana);

}
