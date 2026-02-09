package com.example.studijskiprogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modul")
public class Modul {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false, length = 50)
    private String oznaka;

    @Column(nullable = false)
    private int godina;

    @Column(name = "grupa_naziv", length = 255)
    private String grupaNaziv;

    @ManyToOne(optional = false)
    @JoinColumn(name = "studijski_program_sifra", nullable = false)
    @JsonIgnore
    private StudijskiProgram studijskiProgram;

    @OneToMany(mappedBy = "modul", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanStavka> planStavke = new ArrayList<>();

    public Modul() {
    }

    public Modul(Long id, String naziv, String oznaka, int godina, String grupaNaziv, StudijskiProgram studijskiProgram, List<PlanStavka> planStavke) {
        this.id = id;
        this.naziv = naziv;
        this.oznaka = oznaka;
        this.godina = godina;
        this.grupaNaziv = grupaNaziv;
        this.studijskiProgram = studijskiProgram;
        this.planStavke = planStavke;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public int getGodina() {
        return godina;
    }

    public void setGodina(int godina) {
        this.godina = godina;
    }

    public String getGrupaNaziv() {
        return grupaNaziv;
    }

    public void setGrupaNaziv(String grupaNaziv) {
        this.grupaNaziv = grupaNaziv;
    }

    public StudijskiProgram getStudijskiProgram() {
        return studijskiProgram;
    }

    public void setStudijskiProgram(StudijskiProgram studijskiProgram) {
        this.studijskiProgram = studijskiProgram;
    }

    public List<PlanStavka> getPlanStavke() {
        return planStavke;
    }

    public void setPlanStavke(List<PlanStavka> planStavke) {
        this.planStavke = planStavke;
    }
}
