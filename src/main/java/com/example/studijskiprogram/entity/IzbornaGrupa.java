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

    @Column(name = "potrebni_espb", nullable = false)
    private int potrebniEspb;

    @Column(name = "broj_predmeta", nullable = false)
    private int brojPredmeta;

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

    public IzbornaGrupa() {
    }

    public IzbornaGrupa(Long id, int potrebniEspb, int brojPredmeta, List<PlanStavka> planStavke) {
        this.id = id;
        this.potrebniEspb = potrebniEspb;
        this.brojPredmeta = brojPredmeta;
        this.planStavke = planStavke;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPotrebniEspb() {
        return potrebniEspb;
    }

    public void setPotrebniEspb(int potrebniEspb) {
        this.potrebniEspb = potrebniEspb;
    }

    public int getBrojPredmeta() {
        return brojPredmeta;
    }

    public void setBrojPredmeta(int brojPredmeta) {
        this.brojPredmeta = brojPredmeta;
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
