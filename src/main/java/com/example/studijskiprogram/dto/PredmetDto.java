package com.example.studijskiprogram.dto;


public class PredmetDto {

    private String sifra;
    private String naziv;
    private int espb;
    private int predavanja;
    private int vezbe;
    private int praktikum;
    

    
    public PredmetDto() {
    }

    public PredmetDto(String sifra, String naziv, int espb, int predavanja, int vezbe, int praktikum) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.espb = espb;
        this.predavanja = predavanja;
        this.vezbe = vezbe;
        this.praktikum = praktikum;
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
