package org.example.appcondidature.Repos;

import org.example.appcondidature.entities.LigneFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneFactureRepo extends JpaRepository<LigneFacture, Long> {

}
