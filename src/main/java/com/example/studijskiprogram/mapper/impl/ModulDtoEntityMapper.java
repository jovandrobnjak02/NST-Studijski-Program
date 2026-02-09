package com.example.studijskiprogram.mapper.impl;

import org.springframework.stereotype.Component;
import com.example.studijskiprogram.dto.ModulDto;
import com.example.studijskiprogram.dto.PlanStavkaDto;
import com.example.studijskiprogram.entity.Modul;
import com.example.studijskiprogram.entity.StudijskiProgram;
import com.example.studijskiprogram.entity.PlanStavka;
import com.example.studijskiprogram.mapper.DtoEntityMapper;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModulDtoEntityMapper implements DtoEntityMapper<ModulDto, Modul> {

    private final PlanStavkaDtoEntityMapper planStavkaDtoEntityMapper;

    public ModulDtoEntityMapper(PlanStavkaDtoEntityMapper planStavkaDtoEntityMapper) {
        this.planStavkaDtoEntityMapper = planStavkaDtoEntityMapper;
    }

    @Override
    public ModulDto toDto(Modul entity) {
        if (entity == null) {
            return null;
        }
        List<PlanStavkaDto> planStavke = new ArrayList<>();
        for (var stavka : entity.getPlanStavke()) {
            planStavke.add(planStavkaDtoEntityMapper.toDto(stavka));
        }
        return new ModulDto(
                entity.getId(),
                entity.getNaziv(),
                entity.getOznaka(),
                entity.getGodina(),
                entity.getGrupaNaziv(),
                planStavke
        );
    }

    @Override
    public Modul toEntity(ModulDto dto) {
        throw new IllegalStateException("Use toEntity(ModulDto, StudijskiProgram) when creating modules.");
    }

    public Modul toEntity(ModulDto dto, StudijskiProgram studijskiProgram) {
        if (dto == null) {
            return null;
        }
        List<PlanStavka> planStavke = new ArrayList<>();
        Modul modul = new Modul(
                dto.getId(),
                dto.getNaziv(),
                dto.getOznaka(),
                dto.getGodina(),
                dto.getGrupaNaziv(),
                studijskiProgram,
                planStavke
        );
        if (dto.getPlanStavke() != null) {
            for (PlanStavkaDto planStavkaDto : dto.getPlanStavke()) {
                planStavke.add(planStavkaDtoEntityMapper.toEntity(planStavkaDto, modul));
            }
        }
        return modul;
    }
}
