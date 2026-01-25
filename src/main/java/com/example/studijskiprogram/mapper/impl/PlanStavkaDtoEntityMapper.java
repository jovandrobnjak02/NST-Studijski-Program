package com.example.studijskiprogram.mapper.impl;

import com.example.studijskiprogram.dto.IzbornaGrupaDto;
import com.example.studijskiprogram.dto.PlanStavkaDto;
import com.example.studijskiprogram.entity.IzbornaGrupa;
import com.example.studijskiprogram.entity.Modul;
import com.example.studijskiprogram.entity.PlanStavka;
import com.example.studijskiprogram.mapper.DtoEntityMapper;
import org.springframework.stereotype.Component;

@Component
public class PlanStavkaDtoEntityMapper implements DtoEntityMapper<PlanStavkaDto, PlanStavka> {

    private final IzbornaGrupaDtoEntityMapper izbornaGrupaDtoEntityMapper;

    public PlanStavkaDtoEntityMapper(IzbornaGrupaDtoEntityMapper izbornaGrupaDtoEntityMapper) {
        this.izbornaGrupaDtoEntityMapper = izbornaGrupaDtoEntityMapper;
    }

    @Override
    public PlanStavkaDto toDto(PlanStavka entity) {
        if (entity == null) {
            return null;
        }
        String predmetSifra = entity.getPredmet() == null ? null : entity.getPredmet().getSifra();
        IzbornaGrupaDto izbornaGrupa = izbornaGrupaDtoEntityMapper.toDto(entity.getIzbornaGrupa());
        return new PlanStavkaDto(
                entity.getId(),
                entity.getSemestar(),
                entity.getTip(),
                entity.isAktivan(),
                predmetSifra,
                izbornaGrupa
        );
    }

    @Override
    public PlanStavka toEntity(PlanStavkaDto dto) {
        throw new IllegalStateException("Use toEntity(PlanStavkaDto, Modul) when creating plan items.");
    }

    public PlanStavka toEntity(PlanStavkaDto dto, Modul modul) {
        if (dto == null) {
            return null;
        }
        IzbornaGrupa izbornaGrupa = izbornaGrupaDtoEntityMapper.toEntity(dto.getIzbornaGrupa());
        return new PlanStavka(
                dto.getId(),
                dto.getSemestar(),
                dto.getTip(),
                dto.isAktivan(),
                modul,
                null,
                izbornaGrupa
        );
    }
}
