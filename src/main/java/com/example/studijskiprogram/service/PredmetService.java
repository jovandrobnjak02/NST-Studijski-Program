package com.example.studijskiprogram.service;
import com.example.studijskiprogram.dto.PredmetDto;
import java.util.List;

public interface PredmetService {
    List<PredmetDto> getAllPredmeti();
    PredmetDto findBySifra(String sifra);
    PredmetDto findByNaziv(String naziv);
}
