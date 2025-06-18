package org.example.appcondidature.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LigneFacture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private int quantite;

    private double prixUnitaireHT;

    private double tauxTVA;

    @ManyToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;


}
