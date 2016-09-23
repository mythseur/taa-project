package fr.istic.taa.repository;

import fr.istic.taa.domain.DonneesEntreprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the DonneesEntreprise entity.
 */
@SuppressWarnings("unused")
public interface DonneesEntrepriseRepository extends JpaRepository<DonneesEntreprise, Long> {

}
