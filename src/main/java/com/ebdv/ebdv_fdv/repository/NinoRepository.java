package com.ebdv.ebdv_fdv.repository;

import com.ebdv.ebdv_fdv.model.Nino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NinoRepository extends JpaRepository <Nino, Long> {
    List<Nino> findByActivoTrue();
    List<Nino> findByNombreCompletoContainingIgnoreCase(String nombre);
    List<Nino> findByGrupo(String grupo);
}