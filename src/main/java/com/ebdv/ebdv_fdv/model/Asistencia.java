package com.ebdv.ebdv_fdv.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "asistencias", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nino_id", "dia_semana"})
})
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nino_id", nullable = false)
    private Nino nino;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    private boolean asistio;
    private LocalDate fecha;

    public Asistencia(){}

    public Asistencia(Nino nino, String diaSemana, boolean asistio) {
        this.nino = nino;
        this.diaSemana = diaSemana;
        this.asistio = asistio;
        this.fecha = LocalDate.now();
    }

    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nino getNino() {
        return nino;
    }

    public void setNino(Nino nino) {
        this.nino = nino;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public boolean isAsistio() {
        return asistio;
    }

    public void setAsistio(boolean asistio) {
        this.asistio = asistio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
