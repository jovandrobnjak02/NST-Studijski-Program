package com.example.studijskiprogram.dto;

import com.example.studijskiprogram.entity.PlanStavkaTip;

public class PlanStavkaDto {

    private Long id;
    private int semestar;
    private PlanStavkaTip tip;
    private boolean aktivan;
    private String predmetSifra;
    private IzbornaGrupaDto izbornaGrupa;

    public PlanStavkaDto() {
    }

    public PlanStavkaDto(Long id, int semestar, PlanStavkaTip tip, boolean aktivan, String predmetSifra, IzbornaGrupaDto izbornaGrupa) {
        this.id = id;
        this.semestar = semestar;
        this.tip = tip;
        this.aktivan = aktivan;
        this.predmetSifra = predmetSifra;
        this.izbornaGrupa = izbornaGrupa;
    }

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

    public String getPredmetSifra() {
        return predmetSifra;
    }

    public void setPredmetSifra(String predmetSifra) {
        this.predmetSifra = predmetSifra;
    }

    public IzbornaGrupaDto getIzbornaGrupa() {
        return izbornaGrupa;
    }

    public void setIzbornaGrupa(IzbornaGrupaDto izbornaGrupa) {
        this.izbornaGrupa = izbornaGrupa;
    }
}
