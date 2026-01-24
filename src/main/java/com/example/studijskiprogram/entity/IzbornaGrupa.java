package com.example.studijskiprogram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "min_espb", nullable = false)
    private int minEspb;

    @Column(name = "max_espb", nullable = false)
    private int maxEspb;

    @OneToMany(mappedBy = "izbornaGrupa")
    private List<PlanStavka> planStavke = new ArrayList<>();

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

    public int getMinEspb() {
        return minEspb;
    }

    public void setMinEspb(int minEspb) {
        this.minEspb = minEspb;
    }

    public int getMaxEspb() {
        return maxEspb;
    }

    public void setMaxEspb(int maxEspb) {
        this.maxEspb = maxEspb;
    }

    public List<PlanStavka> getPlanStavke() {
        return planStavke;
    }

    public void setPlanStavke(List<PlanStavka> planStavke) {
        this.planStavke = planStavke;
    }
}
