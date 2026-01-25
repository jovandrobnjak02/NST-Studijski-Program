package com.example.studijskiprogram.mapper.impl;

import org.springframework.stereotype.Component;
import com.example.studijskiprogram.dto.ModulDto;
import com.example.studijskiprogram.entity.Modul;
import com.example.studijskiprogram.entity.StudijskiProgram;
import com.example.studijskiprogram.mapper.DtoEntityMapper;

@Component
public class ModulDtoEntityMapper implements DtoEntityMapper<ModulDto, Modul> {

    @Override
    public ModulDto toDto(Modul entity) {
        if (entity == null) {
            return null;
        }
        return new ModulDto(
                entity.getId(),
                entity.getNaziv(),
                entity.getOznaka(),
                entity.getGodina()
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
        Modul modul = new Modul();
        modul.setId(dto.getId());
        modul.setNaziv(dto.getNaziv());
        modul.setOznaka(dto.getOznaka());
        modul.setGodina(dto.getGodina());
        modul.setStudijskiProgram(studijskiProgram);
        return modul;
    }
}
