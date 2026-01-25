package com.example.studijskiprogram.repository;

import com.example.studijskiprogram.entity.Predmet;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredmetRepository extends JpaRepository<Predmet, String> {
    Predmet findBySifra(String sifra);
    Predmet findByNaziv(String naziv);
    List<Predmet> findBySifraIn(Collection<String> sifre);
}
