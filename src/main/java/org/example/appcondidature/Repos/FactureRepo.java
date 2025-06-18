package org.example.appcondidature.Repos;

import org.example.appcondidature.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactureRepo extends JpaRepository<Facture, Long> {
}
