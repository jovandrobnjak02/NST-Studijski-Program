package com.example.studijskiprogram.repository;

import com.example.studijskiprogram.entity.Predmet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredmetRepository extends JpaRepository<Predmet, String> {
    Predmet findBySifra(String sifra);
    Predmet findByNaziv(String naziv);
}
