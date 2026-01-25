package com.example.studijskiprogram.mapper.impl;

import com.example.studijskiprogram.dto.IzbornaGrupaDto;
import com.example.studijskiprogram.entity.IzbornaGrupa;
import com.example.studijskiprogram.entity.Predmet;
import com.example.studijskiprogram.mapper.DtoEntityMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class IzbornaGrupaDtoEntityMapper implements DtoEntityMapper<IzbornaGrupaDto, IzbornaGrupa> {

    public IzbornaGrupaDtoEntityMapper() {
    }

    @Override
    public IzbornaGrupaDto toDto(IzbornaGrupa entity) {
        if (entity == null) {
            return null;
        }
        List<String> predmetSifre = new ArrayList<>();
        if (entity.getPredmeti() != null) {
            for (Predmet predmet : entity.getPredmeti()) {
                predmetSifre.add(predmet.getSifra());
            }
        }
        return new IzbornaGrupaDto(
                entity.getId(),
                entity.getPotrebniEspb(),
                entity.getBrojPredmeta(),
                predmetSifre
        );
    }

    @Override
    public IzbornaGrupa toEntity(IzbornaGrupaDto dto) {
        if (dto == null) {
            return null;
        }
        return new IzbornaGrupa(dto.getId(), dto.getPotrebniEspb(), dto.getBrojPredmeta(), new ArrayList<>());
    }
}
