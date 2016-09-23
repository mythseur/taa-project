package fr.istic.taa.repository;

import fr.istic.taa.domain.DonneesEtudiant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DonneesEtudiant entity.
 */
@SuppressWarnings("unused")
public interface DonneesEtudiantRepository extends JpaRepository<DonneesEtudiant, Long> {

}
