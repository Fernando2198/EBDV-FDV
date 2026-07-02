package com.ebdv.ebdv_fdv.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ninos")
public class Nino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    private int edad;
    private String grupo;

    @Column(columnDefinition = "TEXT")
    private String notasAdicionales;

    @Column
    private boolean activo = true;

    @OneToMany(mappedBy = "nino", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;

    //Metodo automático al insertar en la DB
    @PrePersist
    protected void onCreate() {
        this.activo = true;
    }

    public boolean tieneAsistencia (String dia) {
        if (this.asistencias == null) return false;
        return this.asistencias.stream().anyMatch(a -> a.getDiaSemana().equalsIgnoreCase(dia) && a.isAsistio());
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public boolean getActivo() {return activo;}

    public void setActivo(boolean activo) { this.activo = activo; }

    public List<Asistencia> getAsistencia() {return asistencias;}

    public void setAsistencias(List<Asistencia> asistencias) {this.asistencias = asistencias;}

}