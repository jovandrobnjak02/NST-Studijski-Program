package com.example.studijskiprogram.mapper.impl;

import org.springframework.stereotype.Component;
import com.example.studijskiprogram.dto.PredmetDto;
import com.example.studijskiprogram.entity.Predmet;
import com.example.studijskiprogram.mapper.DtoEntityMapper;

@Component
public class PredmetDtoMapper implements DtoEntityMapper< PredmetDto, Predmet> {

    @Override
    public PredmetDto toDto(Predmet entity) {
        if (entity == null) {
            return null;
        }
        PredmetDto dto = new PredmetDto();
        dto.setSifra(entity.getSifra());
        dto.setNaziv(entity.getNaziv());
        dto.setEspb(entity.getEspb());
        dto.setPredavanja(entity.getPredavanja());
        dto.setVezbe(entity.getVezbe());
        dto.setPraktikum(entity.getPraktikum());
        return dto;
    }

    @Override
    public Predmet toEntity(PredmetDto dto) {
        if (dto == null) {
            return null;
        }
        Predmet entity = new Predmet();
        entity.setSifra(dto.getSifra());
        entity.setNaziv(dto.getNaziv());
        entity.setEspb(dto.getEspb());
        entity.setPredavanja(dto.getPredavanja());
        entity.setVezbe(dto.getVezbe());
        entity.setPraktikum(dto.getPraktikum());
        return entity;
    }
    
}
