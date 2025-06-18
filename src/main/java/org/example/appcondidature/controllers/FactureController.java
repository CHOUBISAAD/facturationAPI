package org.example.appcondidature.controllers;

import org.example.appcondidature.Repos.FactureRepo;
import org.example.appcondidature.dtos.FactureDto;
import org.example.appcondidature.dtos.FactureExportDTO;
import org.example.appcondidature.entities.Facture;
import org.example.appcondidature.services.FactureSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {

    @Autowired
    private FactureSerivce factureSerivce;

    @GetMapping
    public ResponseEntity<?> factures(){
        List<FactureDto> factures = factureSerivce.getFactures();

        return ResponseEntity.ok(factures);
    }

    @PostMapping
    public ResponseEntity<?> createFacture(@RequestBody FactureDto factureDto){
        Facture facture = factureSerivce.createFacture(factureDto);
        if(facture == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(facture);

    }

    @GetMapping("/{id}/export")
    public ResponseEntity<FactureExportDTO> exportFacture(@PathVariable Long id) {
        try {
            FactureExportDTO exportDTO = factureSerivce.exportFactureById(id);
            return ResponseEntity.ok(exportDTO);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }


}
