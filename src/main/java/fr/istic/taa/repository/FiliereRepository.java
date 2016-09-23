package fr.istic.taa.repository;

import fr.istic.taa.domain.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Filiere entity.
 */
@SuppressWarnings("unused")
public interface FiliereRepository extends JpaRepository<Filiere, Long> {

}
