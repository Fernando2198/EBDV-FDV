package com.ebdv.ebdv_fdv.repository;

import com.ebdv.ebdv_fdv.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorRepository extends JpaRepository <Tutor, Long> {}