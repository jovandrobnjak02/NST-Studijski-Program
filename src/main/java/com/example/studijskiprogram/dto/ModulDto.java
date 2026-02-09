package com.example.studijskiprogram.dto;

import java.util.List;

public class ModulDto {

    private Long id;
    private String naziv;
    private String oznaka;
    private int godina;
    private String grupaNaziv;
    private List<PlanStavkaDto> planStavke;

    public ModulDto() {
    }

    public ModulDto(Long id, String naziv, String oznaka, int godina, String grupaNaziv, List<PlanStavkaDto> planStavke) {
        this.id = id;
        this.naziv = naziv;
        this.oznaka = oznaka;
        this.godina = godina;
        this.grupaNaziv = grupaNaziv;
        this.planStavke = planStavke;
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

    public String getGrupaNaziv() {
        return grupaNaziv;
    }

    public void setGrupaNaziv(String grupaNaziv) {
        this.grupaNaziv = grupaNaziv;
    }

    public List<PlanStavkaDto> getPlanStavke() {
        return planStavke;
    }

    public void setPlanStavke(List<PlanStavkaDto> planStavke) {
        this.planStavke = planStavke;
    }
}
