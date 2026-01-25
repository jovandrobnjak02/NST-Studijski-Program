package com.example.studijskiprogram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.studijskiprogram.service.PredmetService;

@RestController
@RequestMapping("/api/predmeti")
public class PredmetController {

    private PredmetService predmetService;

    public PredmetController(PredmetService predmetService) {
        this.predmetService = predmetService;
    }

    @GetMapping()
    public Object getAllPredmeti() {
        return predmetService.getAllPredmeti();
    }

    @GetMapping("/sifra")
    public Object getPredmetBySifra(String sifra) {
        return predmetService.findBySifra(sifra);
    }

    @GetMapping("/naziv")
    public Object getPredmetByNaziv(String naziv) {
        return predmetService.findByNaziv(naziv);
    }
    
}
