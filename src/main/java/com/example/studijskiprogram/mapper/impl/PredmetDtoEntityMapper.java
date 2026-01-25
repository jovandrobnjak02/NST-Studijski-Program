package com.example.studijskiprogram.mapper.impl;

import org.springframework.stereotype.Component;
import com.example.studijskiprogram.dto.PredmetDto;
import com.example.studijskiprogram.entity.Predmet;
import com.example.studijskiprogram.mapper.DtoEntityMapper;

@Component
public class PredmetDtoEntityMapper implements DtoEntityMapper< PredmetDto, Predmet> {

    @Override
    public PredmetDto toDto(Predmet entity){
        if (entity == null) {
            return null;
        }
        return new PredmetDto(
            entity.getSifra(),
            entity.getNaziv(),
            entity.getEspb(),
            entity.getPredavanja(),
            entity.getVezbe(),
            entity.getPraktikum()
        );
    }

    @Override
    public Predmet toEntity(PredmetDto dto) {
        if (dto == null) {
            return null;
        }
        return new Predmet(
            dto.getSifra(),
            dto.getNaziv(),
            dto.getEspb(),
            dto.getPredavanja(),
            dto.getVezbe(),
            dto.getPraktikum()
        );
    }
    
}
