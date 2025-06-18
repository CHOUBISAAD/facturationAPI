package org.example.appcondidature.dtos;

import java.time.LocalDate;
import java.util.List;

public class FactureExportDTO {
    public Long id;
    public LocalDate date;

    public ClientDto client;

    public List<LigneFactureDTO> lignes;

    public double totalHT;
    public double totalTVA;
    public double totalTTC;
}
