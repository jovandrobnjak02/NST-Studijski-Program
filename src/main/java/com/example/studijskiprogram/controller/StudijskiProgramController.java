package com.example.studijskiprogram.controller;

import com.example.studijskiprogram.dto.StudijskiProgramDto;
import com.example.studijskiprogram.service.StudijskiProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/studijski-programi")
public class StudijskiProgramController {

    private final StudijskiProgramService studijskiProgramService;

    public StudijskiProgramController(StudijskiProgramService studijskiProgramService) {
        this.studijskiProgramService = studijskiProgramService;
    }

    @GetMapping
    @Operation(summary = "Lista svih studijskih programa")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Studijski programi vraceni")
    })
    public List<StudijskiProgramDto> getAll() {
        return studijskiProgramService.getAll();
    }

    @GetMapping("/{sifra}")
    @Operation(summary = "Vrati studijski program po sifri")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Studijski program vracen"),
            @ApiResponse(responseCode = "400", description = "Neispravna sifra")
    })
    public StudijskiProgramDto getBySifra(@PathVariable String sifra) {
        return studijskiProgramService.getBySifra(sifra);
    }

    @PostMapping
    @Operation(summary = "Kreiraj studijski program sa modulima i planom")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Studijski program kreiran"),
            @ApiResponse(responseCode = "400", description = "Neispravan payload")
    })
    public StudijskiProgramDto create(@RequestBody StudijskiProgramDto dto) {
        return studijskiProgramService.create(dto);
    }

    @PutMapping("/{sifra}")
    @Operation(summary = "Azuriraj studijski program po sifri")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Studijski program azuriran"),
            @ApiResponse(responseCode = "400", description = "Neispravan payload")
    })
    public StudijskiProgramDto update(@PathVariable String sifra, @RequestBody StudijskiProgramDto dto) {
        return studijskiProgramService.update(sifra, dto);
    }

    @DeleteMapping("/{sifra}")
    @Operation(summary = "Obrisi studijski program po sifri")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Studijski program obrisan"),
            @ApiResponse(responseCode = "400", description = "Neispravna sifra")
    })
    public void delete(@PathVariable String sifra) {
        studijskiProgramService.delete(sifra);
    }
}
