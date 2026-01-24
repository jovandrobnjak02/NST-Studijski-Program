package com.example.studijskiprogram.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "studijski_program")
public class StudijskiProgram {
    @Id
    @Column(length = 20)
    private String sifra;

    @Column(nullable = false)
    private String naziv;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudijskiProgramStepen stepen;

    @Column(name = "ukupno_espb", nullable = false)
    private int ukupnoEspb;

    @OneToMany(mappedBy = "studijskiProgram", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Modul> moduli = new ArrayList<>();

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

    public StudijskiProgramStepen getStepen() {
        return stepen;
    }

    public void setStepen(StudijskiProgramStepen stepen) {
        this.stepen = stepen;
    }

    public int getUkupnoEspb() {
        return ukupnoEspb;
    }

    public void setUkupnoEspb(int ukupnoEspb) {
        this.ukupnoEspb = ukupnoEspb;
    }

    public List<Modul> getModuli() {
        return moduli;
    }

    public void setModuli(List<Modul> moduli) {
        this.moduli = moduli;
    }
}
