package com.example.studijskiprogram.service;

import com.example.studijskiprogram.entity.StudijskiProgram;
import java.util.List;
import java.util.Optional;

public interface StudijskiProgramService {
    List<StudijskiProgram> findAll();

    Optional<StudijskiProgram> findBySifra(String sifra);

    StudijskiProgram save(StudijskiProgram studijskiProgram);

    void deleteBySifra(String sifra);

    boolean existsBySifra(String sifra);
}
