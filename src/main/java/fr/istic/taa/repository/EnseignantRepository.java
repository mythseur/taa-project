package fr.istic.taa.repository;

import fr.istic.taa.domain.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Enseignant entity.
 */
@SuppressWarnings("unused")
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {

}
