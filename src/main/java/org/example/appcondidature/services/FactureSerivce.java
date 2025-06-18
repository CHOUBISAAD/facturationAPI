package org.example.appcondidature.services;

import jakarta.transaction.Transactional;
import org.example.appcondidature.Repos.ClientRepo;
import org.example.appcondidature.Repos.FactureRepo;
import org.example.appcondidature.dtos.ClientDto;
import org.example.appcondidature.dtos.FactureDto;
import org.example.appcondidature.dtos.FactureExportDTO;
import org.example.appcondidature.dtos.LigneFactureDTO;
import org.example.appcondidature.entities.Client;
import org.example.appcondidature.entities.Facture;
import org.example.appcondidature.entities.LigneFacture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FactureSerivce {

    @Autowired
    private FactureRepo factureRepo;
    @Autowired
    private ClientRepo clientRepo;

    public List<FactureDto> getFactures() {
        return factureRepo.findAll().stream().map(fact -> {
            FactureDto dto = new FactureDto();
            dto.id = fact.getId();
            dto.date = fact.getDate();
            dto.client_id = fact.getClient().getId();

            dto.lignes = fact.getLignes().stream().map(lf -> {
                LigneFactureDTO l = new LigneFactureDTO();
                l.description = lf.getDescription();
                l.quantite = lf.getQuantite();
                l.prixUnitaireHT = lf.getPrixUnitaireHT();
                l.tauxTVA = lf.getTauxTVA();
                return l;
            }).collect(Collectors.toList());

            return dto;
        }).collect(Collectors.toList());
    }



    @Transactional
    public Facture createFacture(FactureDto dto) {
        Client client = clientRepo.findById(dto.client_id)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        Facture facture = new Facture();
        facture.setClient(client);

        double totalHT = 0;
        double totalTVA = 0;

        List<LigneFacture> lignes = new ArrayList<>();

        //on doit parcourir toutes les lignes pour  calcule HT eT TVA  totale
        for (LigneFactureDTO ligneDTO : dto.lignes) {

            LigneFacture ligne = new LigneFacture();
            ligne.setDescription(ligneDTO.description);
            ligne.setQuantite(ligneDTO.quantite);
            ligne.setPrixUnitaireHT(ligneDTO.prixUnitaireHT);
            ligne.setTauxTVA(ligneDTO.tauxTVA);
            ligne.setFacture(facture);

            double ligneTotalHT = ligneDTO.quantite * ligneDTO.prixUnitaireHT;
            double ligneTVA = ligneTotalHT * (ligneDTO.tauxTVA / 100.0);

            totalHT += ligneTotalHT;
            totalTVA += ligneTVA;

            lignes.add(ligne);
        }

        facture.setTotalHT(totalHT);
        facture.setTotalTVA(totalTVA);
        facture.setTotalTTC(totalHT + totalTVA);
        facture.setLignes(lignes);

        return factureRepo.save(facture);
    }
    public FactureExportDTO exportFactureById(Long id) {

        //Recuperation du facture de la base de donnees
        Facture facture = factureRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        // creation de l'objet à exporté
        FactureExportDTO export = new FactureExportDTO();
        export.id = facture.getId();
        export.date = facture.getDate();

        // on cree le client DTO a partir de client
        Client client = facture.getClient();
        ClientDto clientDTO = new ClientDto();
        clientDTO.id = client.getId();
        clientDTO.nom = client.getNom();
        clientDTO.email = client.getEmail();
        clientDTO.siret = client.getSiret();
        clientDTO.dateCreation = client.getDateCreation();


        export.client = clientDTO;

        // creation des lignes
        List<LigneFactureDTO> lignes = new ArrayList<>();

        for (LigneFacture ligne : facture.getLignes()) {
            LigneFactureDTO l = new LigneFactureDTO();
            l.description = ligne.getDescription();
            l.quantite = ligne.getQuantite();
            l.prixUnitaireHT = ligne.getPrixUnitaireHT();
            l.tauxTVA = ligne.getTauxTVA();
            lignes.add(l);
        }
        export.lignes = lignes;

        export.totalHT = facture.getTotalHT();
        export.totalTVA = facture.getTotalTVA();
        export.totalTTC = facture.getTotalTTC();

        return export;
    }



}
