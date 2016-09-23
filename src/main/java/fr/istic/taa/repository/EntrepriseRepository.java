package fr.istic.taa.repository;

import fr.istic.taa.domain.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Entreprise entity.
 */
@SuppressWarnings("unused")
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {

}
