package com.example.studijskiprogram.dto;

import java.util.List;

public class IzbornaGrupaDto {

    private Long id;
    private int potrebniEspb;
    private int brojPredmeta;
    private List<String> predmetSifre;

    public IzbornaGrupaDto() {
    }

    public IzbornaGrupaDto(Long id, int potrebniEspb, int brojPredmeta, List<String> predmetSifre) {
        this.id = id;
        this.potrebniEspb = potrebniEspb;
        this.brojPredmeta = brojPredmeta;
        this.predmetSifre = predmetSifre;
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

    public List<String> getPredmetSifre() {
        return predmetSifre;
    }

    public void setPredmetSifre(List<String> predmetSifre) {
        this.predmetSifre = predmetSifre;
    }
}
