package com.example.studijskiprogram.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan_stavka")
public class PlanStavka {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int semestar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanStavkaTip tip;

    @Column(nullable = false)
    private boolean aktivan;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modul_id", nullable = false)
    @JsonIgnore
    private Modul modul;

    @ManyToOne(optional = false)
    @JoinColumn(name = "predmet_sifra", nullable = false)
    private Predmet predmet;

    @ManyToOne
    @JoinColumn(name = "izborna_grupa_id")
    @JsonIgnore
    private IzbornaGrupa izbornaGrupa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSemestar() {
        return semestar;
    }

    public void setSemestar(int semestar) {
        this.semestar = semestar;
    }

    public PlanStavkaTip getTip() {
        return tip;
    }

    public void setTip(PlanStavkaTip tip) {
        this.tip = tip;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public Modul getModul() {
        return modul;
    }

    public void setModul(Modul modul) {
        this.modul = modul;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public IzbornaGrupa getIzbornaGrupa() {
        return izbornaGrupa;
    }

    public void setIzbornaGrupa(IzbornaGrupa izbornaGrupa) {
        this.izbornaGrupa = izbornaGrupa;
    }
}
