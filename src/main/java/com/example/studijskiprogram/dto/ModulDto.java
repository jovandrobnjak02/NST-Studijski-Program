package com.example.studijskiprogram.dto;

public class ModulDto {

    private Long id;
    private String naziv;
    private String oznaka;
    private int godina;

    public ModulDto() {
    }

    public ModulDto(Long id, String naziv, String oznaka, int godina) {
        this.id = id;
        this.naziv = naziv;
        this.oznaka = oznaka;
        this.godina = godina;
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
}
