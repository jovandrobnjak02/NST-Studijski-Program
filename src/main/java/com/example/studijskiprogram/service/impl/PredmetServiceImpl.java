package com.example.studijskiprogram.service.impl;

import org.springframework.stereotype.Service;
import com.example.studijskiprogram.service.PredmetService;
import com.example.studijskiprogram.repository.PredmetRepository;
import com.example.studijskiprogram.entity.Predmet;
import com.example.studijskiprogram.mapper.impl.PredmetDtoEntityMapper;
import com.example.studijskiprogram.dto.PredmetDto;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredmetServiceImpl implements PredmetService {
    
    private PredmetRepository predmetRepository;
    private PredmetDtoEntityMapper predmetDtoEntityMapper;

    public PredmetServiceImpl(PredmetRepository predmetRepository, PredmetDtoEntityMapper predmetDtoEntityMapper) {
        this.predmetRepository = predmetRepository;
        this.predmetDtoEntityMapper = predmetDtoEntityMapper;
    }

    @Override
    public List<PredmetDto> getAllPredmeti() {

        List<Predmet> predmeti = predmetRepository.findAll();
        
        List<PredmetDto> predmetDto = new ArrayList<>();
        for (Predmet predmet : predmeti) {
            predmetDto.add(predmetDtoEntityMapper.toDto(predmet));
        }
        return predmetDto;
    }

    @Override
    public PredmetDto findBySifra(String sifra) {
        Predmet predmet = predmetRepository.findBySifra(sifra);
        return predmetDtoEntityMapper.toDto(predmet);
    }

    @Override
    public PredmetDto findByNaziv(String naziv) {
        Predmet predmet = predmetRepository.findByNaziv(naziv);
        return predmetDtoEntityMapper.toDto(predmet);
    }
}