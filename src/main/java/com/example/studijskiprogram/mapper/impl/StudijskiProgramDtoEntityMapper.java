package com.example.studijskiprogram.mapper.impl;

import com.example.studijskiprogram.dto.ModulDto;
import com.example.studijskiprogram.dto.StudijskiProgramDto;
import com.example.studijskiprogram.entity.Modul;
import com.example.studijskiprogram.entity.StudijskiProgram;
import com.example.studijskiprogram.mapper.DtoEntityMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class StudijskiProgramDtoEntityMapper implements DtoEntityMapper<StudijskiProgramDto, StudijskiProgram> {

    private final ModulDtoEntityMapper modulDtoEntityMapper;

    public StudijskiProgramDtoEntityMapper(ModulDtoEntityMapper modulDtoEntityMapper) {
        this.modulDtoEntityMapper = modulDtoEntityMapper;
    }

    @Override
    public StudijskiProgramDto toDto(StudijskiProgram entity) {
        if (entity == null) {
            return null;
        }
        List<ModulDto> moduli = new ArrayList<>();
        for (Modul modul : entity.getModuli()) {
            moduli.add(modulDtoEntityMapper.toDto(modul));
        }
        return new StudijskiProgramDto(
                entity.getSifra(),
                entity.getNaziv(),
                entity.getStepen(),
                entity.getUkupnoEspb(),
                moduli
        );
    }

    @Override
    public StudijskiProgram toEntity(StudijskiProgramDto dto) {
        if (dto == null) {
            return null;
        }
        StudijskiProgram entity = new StudijskiProgram();
        entity.setSifra(dto.getSifra());
        entity.setNaziv(dto.getNaziv());
        entity.setStepen(dto.getStepen());
        entity.setUkupnoEspb(dto.getUkupnoEspb());
        List<Modul> moduli = new ArrayList<>();
        if (dto.getModuli() != null) {
            for (ModulDto modulDto : dto.getModuli()) {
                Modul modul = modulDtoEntityMapper.toEntity(modulDto, entity);
                moduli.add(modul);
            }
        }
        entity.setModuli(moduli);
        return entity;
    }
}
