package com.example.studijskiprogram.service;

import com.example.studijskiprogram.dto.StudijskiProgramDto;
import java.util.List;

public interface StudijskiProgramService {
    List<StudijskiProgramDto> getAll();
    StudijskiProgramDto getBySifra(String sifra);
    StudijskiProgramDto create(StudijskiProgramDto dto);
    StudijskiProgramDto update(String sifra, StudijskiProgramDto dto);
    void delete(String sifra);
}
