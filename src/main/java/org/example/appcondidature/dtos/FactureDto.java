package org.example.appcondidature.dtos;

import lombok.Getter;
import org.example.appcondidature.entities.Client;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FactureDto {
    public Long id;
    public LocalDate date;
    public List<LigneFactureDTO> lignes;
    public Long client_id;
}
