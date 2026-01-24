package com.example.studijskiprogram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "preduslov")
public class Preduslov {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String tip;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uslovni_predmet_sifra", nullable = false)
    private Predmet uslovniPredmet;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ciljni_predmet_sifra", nullable = false)
    private Predmet ciljniPredmet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Predmet getUslovniPredmet() {
        return uslovniPredmet;
    }

    public void setUslovniPredmet(Predmet uslovniPredmet) {
        this.uslovniPredmet = uslovniPredmet;
    }

    public Predmet getCiljniPredmet() {
        return ciljniPredmet;
    }

    public void setCiljniPredmet(Predmet ciljniPredmet) {
        this.ciljniPredmet = ciljniPredmet;
    }
}
