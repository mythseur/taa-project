package fr.istic.taa.repository;

import fr.istic.taa.domain.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Etudiant entity.
 */
@SuppressWarnings("unused")
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {

}
