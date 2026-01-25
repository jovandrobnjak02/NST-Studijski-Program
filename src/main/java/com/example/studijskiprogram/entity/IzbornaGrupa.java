package com.example.studijskiprogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "izborna_grupa")
public class IzbornaGrupa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String naziv;

    @Column(name = "min_izbor", nullable = false)
    private int minIzbor;

    @Column(name = "max_izbor", nullable = false)
    private int maxIzbor;

    @Column(nullable = false)
    private int espb;

    @OneToMany(mappedBy = "izbornaGrupa")
    private List<PlanStavka> planStavke = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "izborna_grupa_predmet",
            joinColumns = @JoinColumn(name = "izborna_grupa_id"),
            inverseJoinColumns = @JoinColumn(name = "predmet_sifra")
    )
    @JsonIgnore
    private Set<Predmet> predmeti = new HashSet<>();

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

    public int getMinIzbor() {
        return minIzbor;
    }

    public void setMinIzbor(int minIzbor) {
        this.minIzbor = minIzbor;
    }

    public int getMaxIzbor() {
        return maxIzbor;
    }

    public void setMaxIzbor(int maxIzbor) {
        this.maxIzbor = maxIzbor;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

    public List<PlanStavka> getPlanStavke() {
        return planStavke;
    }

    public void setPlanStavke(List<PlanStavka> planStavke) {
        this.planStavke = planStavke;
    }

    public Set<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(Set<Predmet> predmeti) {
        this.predmeti = predmeti;
    }
}
