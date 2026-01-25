package com.example.studijskiprogram.dto;

import java.util.List;

import com.example.studijskiprogram.entity.StudijskiProgramStepen;

public class StudijskiProgramDto {
    
    private String sifra;
    private String naziv;
    private StudijskiProgramStepen stepen;
    private int ukupnoEspb;
    private List<ModulDto> moduli;

    public StudijskiProgramDto() {
    }

    public StudijskiProgramDto(String sifra, String naziv, StudijskiProgramStepen stepen, int ukupnoEspb, List<ModulDto> moduli) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.stepen = stepen;
        this.ukupnoEspb = ukupnoEspb;
        this.moduli = moduli;
    }

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

    public List<ModulDto> getModuli() {
        return moduli;
    }

    public void setModuli(List<ModulDto> moduli) {
        this.moduli = moduli;
    }
}