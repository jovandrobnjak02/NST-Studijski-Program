package com.example.studijskiprogram.service.impl;

import com.example.studijskiprogram.dto.IzbornaGrupaDto;
import com.example.studijskiprogram.dto.ModulDto;
import com.example.studijskiprogram.dto.PlanStavkaDto;
import com.example.studijskiprogram.dto.StudijskiProgramDto;
import com.example.studijskiprogram.entity.IzbornaGrupa;
import com.example.studijskiprogram.entity.Modul;
import com.example.studijskiprogram.entity.PlanStavka;
import com.example.studijskiprogram.entity.PlanStavkaTip;
import com.example.studijskiprogram.entity.Predmet;
import com.example.studijskiprogram.entity.StudijskiProgram;
import com.example.studijskiprogram.mapper.impl.StudijskiProgramDtoEntityMapper;
import com.example.studijskiprogram.repository.IzbornaGrupaRepository;
import com.example.studijskiprogram.repository.PredmetRepository;
import com.example.studijskiprogram.repository.StudijskiProgramRepository;
import com.example.studijskiprogram.service.StudijskiProgramService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudijskiProgramServiceImpl implements StudijskiProgramService {

    private final StudijskiProgramRepository studijskiProgramRepository;
    private final PredmetRepository predmetRepository;
    private final IzbornaGrupaRepository izbornaGrupaRepository;
    private final StudijskiProgramDtoEntityMapper studijskiProgramDtoEntityMapper;

    public StudijskiProgramServiceImpl(
            StudijskiProgramRepository studijskiProgramRepository,
            PredmetRepository predmetRepository,
            IzbornaGrupaRepository izbornaGrupaRepository,
            StudijskiProgramDtoEntityMapper studijskiProgramDtoEntityMapper
    ) {
        this.studijskiProgramRepository = studijskiProgramRepository;
        this.predmetRepository = predmetRepository;
        this.izbornaGrupaRepository = izbornaGrupaRepository;
        this.studijskiProgramDtoEntityMapper = studijskiProgramDtoEntityMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudijskiProgramDto> getAll() {
        List<StudijskiProgramDto> results = new ArrayList<>();
        for (StudijskiProgram program : studijskiProgramRepository.findAll()) {
            results.add(studijskiProgramDtoEntityMapper.toDto(program));
        }
        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public StudijskiProgramDto getBySifra(String sifra) {
        StudijskiProgram program = studijskiProgramRepository.findById(sifra)
                .orElseThrow(() -> new IllegalArgumentException("Studijski program nije pronadjen: " + sifra));
        return studijskiProgramDtoEntityMapper.toDto(program);
    }

    @Override
    @Transactional
    public StudijskiProgramDto create(StudijskiProgramDto dto) {
        validateTotalEspb(dto);
        StudijskiProgram program = studijskiProgramDtoEntityMapper.toEntity(dto);
        if (dto.getModuli() == null) {
            program = studijskiProgramRepository.save(program);
            return studijskiProgramDtoEntityMapper.toDto(program);
        }

        List<ModulDto> modulDtos = dto.getModuli();
        List<Modul> moduli = program.getModuli();

        for (int i = 0; i < modulDtos.size(); i++) {
            ModulDto modulDto = modulDtos.get(i);
            Modul modul = moduli.get(i);
            resolvePlanStavke(modulDto, modul);
        }

        program = studijskiProgramRepository.save(program);
        return studijskiProgramDtoEntityMapper.toDto(program);
    }

    @Override
    @Transactional
    public StudijskiProgramDto update(String sifra, StudijskiProgramDto dto) {
        if (!studijskiProgramRepository.existsById(sifra)) {
            throw new IllegalArgumentException("Studijski program nije pronadjen: " + sifra);
        }
        if (dto.getSifra() == null || !dto.getSifra().equals(sifra)) {
            dto.setSifra(sifra);
        }
        validateTotalEspb(dto);
        StudijskiProgram program = studijskiProgramDtoEntityMapper.toEntity(dto);
        if (dto.getModuli() != null) {
            List<ModulDto> modulDtos = dto.getModuli();
            List<Modul> moduli = program.getModuli();
            for (int i = 0; i < modulDtos.size(); i++) {
                ModulDto modulDto = modulDtos.get(i);
                Modul modul = moduli.get(i);
                resolvePlanStavke(modulDto, modul);
            }
        }
        program = studijskiProgramRepository.save(program);
        return studijskiProgramDtoEntityMapper.toDto(program);
    }

    @Override
    @Transactional
    public void delete(String sifra) {
        if (!studijskiProgramRepository.existsById(sifra)) {
            throw new IllegalArgumentException("Studijski program nije pronadjen: " + sifra);
        }
        studijskiProgramRepository.deleteById(sifra);
    }

    private void resolvePlanStavke(ModulDto modulDto, Modul modul) {
        if (modulDto.getPlanStavke() == null) {
            return;
        }
        List<PlanStavka> planStavke = modul.getPlanStavke();
        for (int i = 0; i < modulDto.getPlanStavke().size(); i++) {
            PlanStavkaDto planStavkaDto = modulDto.getPlanStavke().get(i);
            PlanStavka planStavka = planStavke.get(i);
            validatePlanStavka(planStavkaDto);
            int correctedSemestar = normalizeSemestar(planStavkaDto.getSemestar(), modulDto.getGodina());
            planStavka.setSemestar(correctedSemestar);

            if (planStavkaDto.getPredmetSifra() != null) {
                Predmet predmet = predmetRepository.findById(planStavkaDto.getPredmetSifra())
                        .orElseThrow(() -> new IllegalArgumentException("Predmet nije pronadjen: " + planStavkaDto.getPredmetSifra()));
                planStavka.setPredmet(predmet);
                continue;
            }

            IzbornaGrupaDto grupaDto = planStavkaDto.getIzbornaGrupa();
            IzbornaGrupa grupa = planStavka.getIzbornaGrupa();
            Set<Predmet> predmeti = resolvePredmeti(grupaDto);
            grupa.setPredmeti(predmeti);
            izbornaGrupaRepository.save(grupa);
        }
    }

    private void validatePlanStavka(PlanStavkaDto dto) {
        boolean hasPredmet = dto.getPredmetSifra() != null;
        boolean hasGrupa = dto.getIzbornaGrupa() != null;
        if (hasPredmet == hasGrupa) {
            throw new IllegalArgumentException("Plan stavka mora imati ili predmet ili izbornu grupu.");
        }
        if (dto.getTip() == PlanStavkaTip.OBAVEZAN && !hasPredmet) {
            throw new IllegalArgumentException("Obavezna plan stavka mora imati predmet.");
        }
        if (dto.getTip() == PlanStavkaTip.IZBORNI && !hasGrupa) {
            throw new IllegalArgumentException("Izborna plan stavka mora imati izbornu grupu.");
        }
    }

    private int normalizeSemestar(int semestar, int godina) {
        int safeGodina = Math.max(1, godina);
        int minSemestar = (safeGodina - 1) * 2 + 1;
        int maxSemestar = safeGodina * 2;
        if (semestar < minSemestar) {
            return minSemestar;
        }
        if (semestar > maxSemestar) {
            return maxSemestar;
        }
        return semestar;
    }

    private void validateTotalEspb(StudijskiProgramDto dto) {
        if (dto == null || dto.getModuli() == null) {
            return;
        }
        int total = 0;
        for (ModulDto modulDto : dto.getModuli()) {
            if (modulDto.getPlanStavke() == null) {
                continue;
            }
            for (PlanStavkaDto planStavkaDto : modulDto.getPlanStavke()) {
                if (planStavkaDto.getTip() == PlanStavkaTip.OBAVEZAN && planStavkaDto.getPredmetSifra() != null) {
                    Predmet predmet = predmetRepository.findById(planStavkaDto.getPredmetSifra())
                            .orElseThrow(() -> new IllegalArgumentException("Predmet nije pronadjen: " + planStavkaDto.getPredmetSifra()));
                    total += predmet.getEspb();
                    continue;
                }
                if (planStavkaDto.getTip() == PlanStavkaTip.IZBORNI && planStavkaDto.getIzbornaGrupa() != null) {
                    IzbornaGrupaDto grupaDto = planStavkaDto.getIzbornaGrupa();
                    total += grupaDto.getBrojPredmeta() * grupaDto.getPotrebniEspb();
                }
            }
        }
        if (dto.getUkupnoEspb() != total) {
            throw new IllegalArgumentException("Ukupno ESPB mora biti " + total + ".");
        }
    }

    private Set<Predmet> resolvePredmeti(IzbornaGrupaDto grupaDto) {
        if (grupaDto == null || grupaDto.getPredmetSifre() == null || grupaDto.getPredmetSifre().isEmpty()) {
            throw new IllegalArgumentException("Izborna grupa mora sadrzati sifre predmeta.");
        }
        List<Predmet> found = predmetRepository.findBySifraIn(grupaDto.getPredmetSifre());
        if (found.size() != grupaDto.getPredmetSifre().size()) {
            throw new IllegalArgumentException("Neki predmeti nijesu pronadjeni za izbornu grupu.");
        }
        for (Predmet predmet : found) {
            if (predmet.getEspb() != grupaDto.getPotrebniEspb()) {
                throw new IllegalArgumentException("ESPB predmeta se ne poklapa sa zahtjevom izborne grupe.");
            }
        }
        if (grupaDto.getBrojPredmeta() > found.size()) {
            throw new IllegalArgumentException("Broj predmeta je veci od velicine izborne grupe.");
        }
        return new HashSet<>(found);
    }
}
