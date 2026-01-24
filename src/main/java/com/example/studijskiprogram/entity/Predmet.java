package com.example.studijskiprogram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "predmet")
public class Predmet {
    @Id
    @Column(length = 20)
    private String sifra;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private int espb;

    @Column(nullable = false)
    private int predavanja;

    @Column(nullable = false)
    private int vezbe;

    @Column(nullable = false)
    private int praktikum;

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getEspb() {
        return espb;
    }

    public void setEspb(int espb) {
        this.espb = espb;
    }

    public int getPredavanja() {
        return predavanja;
    }

    public void setPredavanja(int predavanja) {
        this.predavanja = predavanja;
    }

    public int getVezbe() {
        return vezbe;
    }

    public void setVezbe(int vezbe) {
        this.vezbe = vezbe;
    }

    public int getPraktikum() {
        return praktikum;
    }

    public void setPraktikum(int praktikum) {
        this.praktikum = praktikum;
    }
}
